mermaid ```
classDiagram
    class Board {
        -posiitonNames: String[]
        -landingCounts: int[]
        +GO_INDEX: int
        +JAIL_INDEX: int
        +GO_TO_JAIL_INDEX: int
        +NUM_SQUARES: int
        +getSquareName(index) String
        +incrementLanding(index)
        +getLandingCounts() int[]
        +nearestRailroad(from) int
        +nearestUtility(from) int
    }

    class Player {
        -position: int
        -inJail: boolean
        -jailTurns: int
        -consecutiveDoubles: int
        -chanceJailCards: int
        -chestJailCards: int
        +getPosition() int
        +setPosition(p)
        +isInJail() boolean
        +incrementJailTurns()
        +resetDoubles()
        +incrementDoubles()
        +goToJail()
        +leaveJail()
        +hasJailCard() boolean
        +addChanceJailCard()
        +addChestJailCard()
        +useJailCard() String
    }

    class Deck {
        -drawPile: List~IntUnaryOperator~
        -discardPile: List~IntUnaryOperator~
        -rand: Random
        +GET_OUT_OF_JAIL: int
        +NO_MOVE: int
        +draw(currentPosition) int
        +returnCard()
        +buildChanceCards(board)$ List
        +buildChestCards()$ List
    }

    class Simulation {
        -board: Board
        -player: Player
        -chanceDeck: Deck
        -chestDeck: Deck
        -rand: Random
        -strategy: int
        +STRATEGY_A: int
        +STRATEGY_B: int
        +rollDice() int[]
        +takeTurn()
        +run(numTurns)
        +getBoard() Board
        +getPlayer() Player
        -handleJailTurn()
        -movementPhase()
        -moveForward(spaces)
        -resolveLanding()
        -drawChanceCard()
        -drawChestCard()
    }

    class Driver {
        -CHECKPOINTS: int[]$
        -NUM_RUNS: int$
        -OUTPUT_DIR: String$
        +main(args)$
        -runStrategy(strategy, label, baseSeed)$ int[][][]
        -writeRunCSV(label, runIndex, board, results)$
        -writeAggregateCSV(label, results)$
        -writeSummary(strategyA, strategyB)$
        -printTop5(pw, results, cpIndex, n, board)$
    }

    Simulation "1" *-- "1" Board : owns
    Simulation "1" *-- "1" Player : owns
    Simulation "1" *-- "2" Deck : owns
    Driver ..> Simulation : creates
    Deck ..> Board : references for card destinations