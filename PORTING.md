# Alto Clef NeoForge 1.21.1

This repository is the NeoForge platform port of Alto Clef. The first milestone
keeps the platform bridge isolated from the task, tracker and chain code.

## Current status

- NeoForge 21.1.235 / Minecraft 1.21.1 MDK is configured.
- `AltoClef` is a client-only safe NeoForge entry point.
- `ClientBootstrap` owns the NeoForge event registrations.
- The official Baritone `1.21.1` branch has been downloaded for inspection. It
  does contain a `neoforge` source set, but its own `gradle.properties` pins
  `minecraft_version=1.21` and `neoforge_version=20-beta`; it is therefore not
  a drop-in NeoForge 21.1 (Minecraft 1.21.1) dependency.

## Next migration slices

1. Add the 1.21.1 Mojmap platform adapters.
2. Connect the internal event bus to client tick, chat, screen and render events.
3. Rebase the official Baritone NeoForge source set onto Minecraft 1.21.1 /
   NeoForge 21.1.x, then publish a local Maven artifact for this project.
4. Move trackers and task chains package-by-package, compiling after each slice.
