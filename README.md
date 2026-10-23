# Group-79---Rummikub
The objective is to be the first player to play all your tiles by forming sets. There are two kinds of sets: a “group”, which is a set of 3 or 4 tiles of the same number but different colors; or a “run”, which is a set of 3-13 consecutive numbers all of the same color (where 1 can’t be preceded by any number, and 13 can’t be followed by any number).

Only for each player’s first move, the player cannot use tiles from already formed groups and a set must sum to 30 minimum. For all turns after their first move, the player can play any of their tiles and move tiles from already formed groups, or draw a random tile.

Objective of game: to clear your rack of tiles

## Running the game

Compile and run from the terminal window:

```sh
cd /workspaces/Group-79---Rummikub
javac *.java
java Game
```

Enter your (the player) name and take turns playing against the computer. An initial meld can contain multiple sets whose points add up to at least 30. On later turns, play a set or end the turn to draw.

Because this is a basic version, it does not yet support rearranging tiles already on the table. It does however allow for adding to an existing set.

## Running the tests

The project uses JUnit 4. If you want to run the test, do this in the project directory with:

```sh
mvn test
```
## Reflection
What changed from your specification?: Classes did not change, but methods did. As we wrote our code, we realized how much some methods need to handle (specially in Player and Game), so we wrote many helper methods that we didn't plan for initially. This all happened through our meetings and individual coding times.

What changed in the LinkedChain?: Despite these changes, the given LinkedChain class wasn't modified at all. Additionally, we need to further implement the part where players choose if they want to draw a random tile instead of playing. We decided on not changing anything of the class through our coversations about the game.

What happened to the unknowns? We figured out some things that we didn't know how to do initially, such as the GUI and the game rules by researching and talking over some of the past things that were done.

What doesn't work yet? As of now, the code only lets the player add to existing sets, not take from them. This means that it can not rearrange existing table tiles, such as reorganizing, but it does let a player add a tile from their hand to an existing set.


## Next Steps

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

| `Game.java` | Sarah, Aaron, Abigel| Aaron wrote `createTiles()` and `shuffleDrawPile()`. Sarah wrote `checkWinner`, `addSetToTable`, and `displayBoard`. Abigel wrote the other methods: `announceWinner`, `takePlayerTurn`, Abigel used GenAI assistance to adapt the game to the two-player prototype and help out with play method. |

| `Group.java` | Abigel, Aaron | Aaron wrote getPoints() method. Abigel wrote rest of the class|

| `LinkedChain.java` | Did not get modified |

| `Player.java` | Enoch, Aaron | Enoch wrote everything; Aaron reviewed it and fixed bugs.|

| `Run.java` | Abigel, Aaron | Aaron wrote getPoints() method. Abigel wrote the rest of the class |

| `Set.java` | Aaron, Sarah | Aaron wrote the isEmpty(), size(), and get() methods. Sarah wrote rest of the class |

| `Tile.java` | Sarah | Wrote the class |

| `Computer.java` | Aaron, Abigel | Wrote the class


| `src/test/java/ComputerTest.java` |Enoch| Tests computer finding valid groups and runs|

| `src/test/java/GroupTest.java` | GenAI-assisted; team reviewed | Tests group validity, duplicate colors, joker groups, and scoring. |

| `src/test/java/LinkedChainTest.java` | GenAI-assisted; team reviewed | Tests insertion order, removal, counting, array conversion, indexing, and clearing. |

| `src/test/java/PlayerTest.java` | GenAI-assisted; team reviewed | Tests initial meld scoring, hand membership, and later plays. |

| `src/test/java/RunTest.java` | GenAI-assisted; team reviewed | Tests run validity, extensions, joker handling, and scoring. |

| `src/test/java/TileTest.java` | GenAI-assisted; team reviewed | Tests tile equality, getters, and display text. |

| `src/test/java/SetTest.java` | GenAI-assisted; team reviewed | Tests that a set of tiles behaves like a container |


### GenAI usage

GenAI assistance was used to help out with play method, and generate most of the JUnit tests. Team members have reviewed and verified AI-assisted changes.

### System Diagram
https://docs.google.com/document/d/16Dh-sPuOrqSh66Iml23WTMzUySfWBzUC3cahu9CiUUI/edit?usp=sharing
