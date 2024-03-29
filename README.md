# 7ROF App
The 7ROF (pronounced Huroof) game is based on an old TV show that was used to play on Al Saudiya TV channel. This program is used as a display for the host to pick letters from. The design, layout, and functionality of the app are inspired by the original show and are enhanced through the use of hexagonal grids and different game layers.

## Hexagonal Grid Structure
The application makes use of a hexagonal grid system to align elements as seen in the original show. You can learn more about hex grids from [this website](https://www.redblobgames.com/grids/hexagons), which has been a valuable resource in implementing this feature.

### Game Layers Structure
The game layout consists of three main layers:
1. **Background Hex Grid**: The foundational layer that holds everything together.
2. **Letters Grid**: Displays the letters and additional graphical elements.
3. **Selection Grid**: Used for applying overlay colors after clicking an element, enhancing the visual feedback.

![Game Layers Structure Diagram](resources/images/layer_structure.png)

## Sample from the TV Show (The project is to recreate this)
![image](https://user-images.githubusercontent.com/30318708/178619545-4c2aa351-80d3-4a98-98e5-88d1de051fac.png "Image from the TV Show")
A better look at the display can be seen in this YouTube video: https://youtu.be/GqCcBNJTTC4?t=250

## Sample from the latest commit
![image](https://user-images.githubusercontent.com/30318708/178620742-63421131-0fd7-49f9-8c33-4c1263d8a650.png "Image from the App")
The display mimics the main functionalities of the screen.

### Observed Functionalities 
A list of the observed functionalities is in the following table:

| Functionality | In Show | In App |
| --- | --- | --- |
| Hexagonal Grids | :white_check_mark: | :white_check_mark: |
| 3D Retro Style | :white_check_mark: | :warning:* |
| Custom Font | :white_check_mark: | :warning:** |
| Green/Red Cells | :white_check_mark: | :white_check_mark: |
| Blinking Selected | :white_check_mark: | :white_check_mark: |
| Score System | :white_check_mark: | :outbox_tray: |
| Sound System | :white_check_mark: | :outbox_tray: |
| Timer System | :white_check_mark: | :outbox_tray: |
| Overlaying/Video editing| :white_check_mark: | :outbox_tray: |
| Animations | :white_check_mark: | :card_file_box: |

*CSS styles in javafx are limited. 
**Exact font could not be identified; a different (but hopefully visually similar) font was chosen.

:outbox_tray: : Handled by glue scripts.

:card_file_box: : Not yet implemented. (TODO)

### Additional Details
The sound system is managed by an AHK script combined with 4 OSU macro keyboards to reflect the button press action on the screen.

In the past (during COVID), we used ![BuzzIn.live](https://buzzin.live/) to mimic the buttons with another AHK script that glued everything together, and the full screen looked like this:
![screenshot](https://user-images.githubusercontent.com/30318708/178625313-70cf767a-3125-4fe4-b6ad-559eefc47827.jpeg)
Yikes? I know.
- Upper middle window is being broadcasted live on YouTube.
- Bottom left is **VoiceMeeter Banana**, used for audio management.
- Bottom middle is **OBS Stream Labs** for broadcasting.
- Most Right is **Discord**, to get the players' webcams.
- Upper left is the **BuzzIn.live** site for the game buzzers session.
- Next to that is an earlier version of the 7ROF App.

## Installation
You first need to add JavaFX libs to the project; after that, just run `App.java`, and the display should be up and running.

Currently, you have to link everything manually, but hopefully (once I refactor the code to work in other systems than mine) I will upload the other scripts too.

___
Have fun, experiment, and if you have any modifications to my work, feel free to make a pull request.
