# Alto Clef NeoForge 1.21.1

This repository is the NeoForge platform port of Alto Clef. The first milestone
keeps the platform bridge isolated from the task, tracker and chain code.

## Current status

- NeoForge 21.1.235 / Minecraft 1.21.1 MDK is configured.
- `AltoClef` is a client-only safe NeoForge entry point.
- `ClientBootstrap` owns the NeoForge event registrations.
- Baritone is intentionally behind a navigation adapter; the upstream project
  publishes Fabric artifacts and cannot be linked directly from NeoForge.

## Next migration slices

1. Add the 1.21.1 Mojmap platform adapters.
2. Connect the internal event bus to client tick, chat, screen and render events.
3. Add a NeoForge-compatible Baritone implementation or adapter.
4. Move trackers and task chains package-by-package, compiling after each slice.
