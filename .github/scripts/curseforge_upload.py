#!/usr/bin/env python3
"""Upload a built mod jar to CurseForge through the upload API.

Environment:
  CURSEFORGE_TOKEN       API token from https://legacy.curseforge.com/account/api-tokens
  CURSEFORGE_PROJECT_ID  Numeric project id, shown in the sidebar of the project page

Example:
  python .github/scripts/curseforge_upload.py build/libs/maid_restaurant-0.3.1-fabric.1.21.11.jar \
      --changelog-file docs/curseforge/changelog/0.3.1-fabric.1.21.11.md --dry-run
"""

from __future__ import annotations

import argparse
import json
import os
import sys
import uuid
from pathlib import Path
from urllib.error import HTTPError
from urllib.request import Request, urlopen

API = "https://minecraft.curseforge.com/api"


def request(url: str, token: str, *, data: bytes | None = None, content_type: str | None = None) -> bytes:
    headers = {"X-Api-Token": token, "User-Agent": "maid-restaurant-release-script"}
    if content_type:
        headers["Content-Type"] = content_type
    try:
        with urlopen(Request(url, data=data, headers=headers), timeout=180) as response:
            return response.read()
    except HTTPError as error:
        body = error.read().decode("utf-8", "replace").strip()
        raise SystemExit(f"error: CurseForge returned HTTP {error.code} for {url}\n{body}")


def encode_multipart(fields: dict[str, str], filename: str, payload: bytes) -> tuple[str, bytes]:
    boundary = uuid.uuid4().hex
    body = bytearray()
    for name, value in fields.items():
        body += f'--{boundary}\r\nContent-Disposition: form-data; name="{name}"\r\n\r\n'.encode()
        body += value.encode("utf-8") + b"\r\n"
    body += (
        f'--{boundary}\r\nContent-Disposition: form-data; name="file"; filename="{filename}"\r\n'
        f"Content-Type: application/java-archive\r\n\r\n"
    ).encode()
    body += payload + b"\r\n"
    body += f"--{boundary}--\r\n".encode()
    return f"multipart/form-data; boundary={boundary}", bytes(body)


def resolve_game_versions(names: list[str], token: str) -> list[int]:
    """Map CurseForge tag names to the numeric ids the upload API expects.

    An unknown name is a hard error: dropping a tag silently would publish the
    file against the wrong loader, Minecraft version or environment.
    """
    versions = json.loads(request(f"{API}/game/versions", token))
    by_name: dict[str, list[dict]] = {}
    for version in versions:
        by_name.setdefault(version["name"], []).append(version)

    ids = []
    for name in names:
        matches = by_name.get(name)
        if not matches:
            raise SystemExit(f'error: no CurseForge game version tag named "{name}"')
        chosen = matches[0]
        print(f"  {name} -> {chosen['id']} (type {chosen['gameVersionTypeID']})")
        ids.append(chosen["id"])
    return ids


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("jar", type=Path, help="the remapped mod jar to upload")
    parser.add_argument("--metadata", type=Path, default=Path(".github/curseforge.json"),
                        help="tag and relation template (default: %(default)s)")
    parser.add_argument("--release-type", choices=("release", "beta", "alpha"),
                        default=os.environ.get("RELEASE_TYPE", "release"))
    parser.add_argument("--display-name", default=os.environ.get("DISPLAY_NAME"),
                        help="file name shown on CurseForge (default: the jar's base name)")
    parser.add_argument("--changelog-file", type=Path, default=None)
    parser.add_argument("--project-id", default=os.environ.get("CURSEFORGE_PROJECT_ID"))
    parser.add_argument("--dry-run", action="store_true",
                        help="resolve and print the metadata without uploading")
    args = parser.parse_args()

    token = os.environ.get("CURSEFORGE_TOKEN")
    if not token:
        raise SystemExit("error: CURSEFORGE_TOKEN is not set")
    if not args.dry_run and not args.project_id:
        raise SystemExit("error: CURSEFORGE_PROJECT_ID is not set")
    if not args.jar.is_file():
        raise SystemExit(f"error: jar not found: {args.jar}")

    template = json.loads(args.metadata.read_text(encoding="utf-8"))

    changelog = ""
    if args.changelog_file:
        if not args.changelog_file.is_file():
            raise SystemExit(f"error: changelog not found: {args.changelog_file}")
        changelog = args.changelog_file.read_text(encoding="utf-8").strip()

    print("Resolved tags:")
    metadata = {
        "changelog": changelog,
        "changelogType": "markdown",
        "displayName": args.display_name or args.jar.stem,
        "gameVersions": resolve_game_versions(template["gameVersions"], token),
        "releaseType": args.release_type,
        "relations": template["relations"],
    }

    if args.dry_run:
        print("--dry-run, not uploading. Metadata that would be sent:")
        print(json.dumps(metadata, indent=2, ensure_ascii=False))
        return 0

    # Escape non-ASCII into JSON unicode escapes: the multipart field carries no
    # charset, so a pure-ASCII body keeps the Chinese changelog from depending on
    # CurseForge guessing the encoding right.
    content_type, body = encode_multipart(
        {"metadata": json.dumps(metadata)},
        args.jar.name,
        args.jar.read_bytes(),
    )
    response = json.loads(request(
        f"{API}/projects/{args.project_id}/upload-file",
        token,
        data=body,
        content_type=content_type,
    ))
    print(f"Uploaded {args.jar.name} as CurseForge file {response['id']}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
