# Group-79---Rummikub
The objective is to be the first player to play all your tiles by forming sets. There are two kinds of sets: a “group”, which is a set of 3 or 4 tiles of the same number but different colors; or a “run”, which is a set of 3-13 consecutive numbers all of the same color (where 1 can’t be preceded by any number, and 13 can’t be followed by any number).

Only for each player’s first move, the player cannot use tiles from already formed groups and a set must sum to 30 minimum. For all turns after their first move, the player can play any of their tiles and move tiles from already formed groups, or draw a random tile.

Objective of game: to clear your rack of tiles

## Running the game

Compile and run from the project directory:

```sh
javac *.java
java Game
```

Enter two player names, then take turns using the numbered hand positions when asked to play a group or run. To take turns, pass the screen between players and avoid looking at the other player's hand. An initial meld can contain multiple sets whose points add up to at least 30. On later turns, play a set or end the turn to draw.

Because this is a basic version, it does not yet support rearranging tiles already on the table.


## Reflection
Classes did not change, but methods did. As we wrote our code, we realized how much some methods need to handle (specially in Player and Game), so we wrote many helper methods that we didn't plan for initially. Despite this, the given LinkedChain class wasn't modified at all. Additionally, we need to further implement the part where players choose if they want to draw a random tile instead of playing.
We also figured out some things that we didn't know how to do initially, such as the GUI and the game rules, but we still haven't figured out how to get the computer to play against the player. For now, the player is playing against another player.


## Next Steps

For our next steps, we'll try to implement the computer class to play against the player, which means a new class to test and potentially new helper methods for that class. (we also need to say who's gonna do what after this) 

Some stretch goals we will try to implement from the scope: 
    - keeping track of score and consequences for not being able to complete a turn
    - improve our GUI using colored text instead of black-and-white console tex
    - having customizable difficulty

Who does what
    - All teammembers will distribute making the computer class
    - Enoch has already made methods that will change GUI colors, and will help teammates implement them into the game class
    - All team members communicate and try to find ways to have customizable difficulty

## Attribution

| Class or test file | Who worked on it | How |
| --- | --- | --- |

| `Game.java` | Sarah, Aaaron, Abigel| Aaron wrote `createTiles()` and `shuffleDrawPile()`. Sarah wrote `checkWinner`, `addSetToTable`, and `displayBoard`. Abigel wrote the other methods: `announceWinner`, `takePlayerTurn`, Abigel used GenAI assistance to adapt the game to the two-player prototype and help out with play method. |

| `Group.java` | Abigel, Aaron | Aaron wrote getPoints() method. Abigel wrote rest of the class|
| `LinkedChain.java` | Did not get modified |
| `Player.java` | Enoch, Aaron | Enoch wrote everything; Aaron reviewed it and fixed bugs.|
| `Run.java` | Abigel, Aaron | Aaron wrote getPoints() method. Abigel wrote the rest of the class |
| `Set.java` | Aaron, Sarah | Aaron wrote the isEmpty(), size(), and get() methods. Sarah wrote rest of the class |
| `Tile.java` | Sarah | Wrote the class |

| Test files | None currently present | No test files are in the project. |

### GenAI usage

GenAI assistance was used to adapt `Game.java` for the two-player prototype and to update this README's attribution and documentation. Team members have reviewed and verified AI-assisted changes.