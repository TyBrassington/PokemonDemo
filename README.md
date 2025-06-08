# Pokemon Diamond Java Demo - Twinleaf Town

A custom 2D game demo built in Grade 12 for my Capstone Project, recreating the starting area, Twinleaf Town, of my favourite childhood game, _Pokemon Diamond_.
The project began with following and learning from a general 2D game engine tutorial in Java, but quickly evolved into my own original recreation of Pokemon.
This is created using only native Java libraries.

<img width="578" alt="image" src="https://github.com/user-attachments/assets/215f6314-5d26-4220-b439-2d42ac56291c" />

---

### Setup

1. Run this in your terminal: `git clone https://github.com/TyBrassington/PokemonDemo`
2. Open the project in your preferred IDE.
3. Navigate to `src/main/Main.java`
4. Run `Main.java` to start the game.
> Note: Please ensure your Java SDK is installed and properly configured

### Features
Twinleaf Town Map: Custom tile-based map with walkable and non-walkable regions.

<img width="578" alt="image" src="https://github.com/user-attachments/assets/4a7bbb79-1491-43cd-bb75-d0535f11a58f" />

Player Controls: Movement with collision detection, animations, and running toggle.

NPC Interactions: Scripted dialogues with rolling text and dialogue arrows.
> Note: To trigger, NPC interaction, hold movement key into them and press space simulateously

<img width="578" alt="image" src="https://github.com/user-attachments/assets/01153f74-3889-46fc-96e2-0115c2664a9b" />

Event System: Custom triggers for events like map transitions, cutscenes, and sounds.

Map Transitions: Teleportation between indoor and outdoor scenes with fade effects.
> Note: The map transition trigger tile is intentionally placed only in the center of the path bottleneck for debugging purposes.
> To trigger it, walk straight up the middle of the dirt path until you collide with the tile.

Day/Night Cycle: Visual filters and lighting effects that simulate time of day (day length to 10 seconds for demo purposes).

Day:

<img width="578" alt="image" src="https://github.com/user-attachments/assets/4034f57a-4cc5-4b96-87e9-dff64ebe0e56" />

Night:

<img width="578" alt="image" src="https://github.com/user-attachments/assets/e8e8123a-4367-42b3-8a58-6f76ff0c3116" />


Audio System: Background music, transition audio, sound effects for interactions.

Debug Tools: Debug UI for tile viewing, hitboxes, and live scene data.

### Known Limitations

The game is not fully polished and contains minor bugs.

Certain gameplay systems (e.g., battles, item menus) are not implemented.

Core gameplay and event triggers were prioritized over full feature completion for demonstration purposes.

---
This project is for educational and demonstration purposes only.
