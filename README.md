# Group-79---Rummikub
The objective is to be the first player to play all your tiles by forming sets. There are two kinds of sets: a “group”, which is a set of 3 or 4 tiles of the same number but different colors; or a “run”, which is a set of 3-13 consecutive numbers all of the same color (where 1 can’t be preceded by any number, and 13 can’t be followed by any number).

Only for each player’s first move, the player cannot use tiles from already formed groups and a set must sum to 30 minimum. For all turns after their first move, the player can play any of their tiles and move tiles from already formed groups, or draw a random tile.

Objective of game: to clear your rack of tiles


## Reflection
Classes did not change, but methods did. As we wrote our code, we realized how much some methods need to handle (specially in Player and Game), so we wrote many helper methods that we didn't plan for initially. Despite this, the given LinkedChain class wasn't modified at all. We also figured out some things that we didn't know how to do initially, such as the GUI and the game rules, but we still haven't figured out how to get the computer to play against the player.


## Next Steps
We still have to make the game correctly handle joker tiles, but most of the MVP is already done. Additionally, we'll try to implement the computer class to play against the player, which means a new class to test and potentially new helper methods for that class. (we also need to say who's gonna do what after this)


Attribution (i think this should be a table but idk how to do that)

Tile: 
Set: Aaron wrote the isEmpty(), size(), and get() methods;
Group: Aaron wrote getPoints() method;
Run: Aaron wrote getPoints() method;
Game: Aaron wrote the createTiles() and shuffleDrawPile() methods; 
Player: Enoch wrote everything; Aaron reviewed and bug fixed.

GenAI usage: 

(feel free to change any of the new stuff btw, i just wrote this by following the instructions in the canvas assignment)