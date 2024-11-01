# Actions to test:

- [ ] [ChallengePlayerAction](#ChallengePlayerAction)
- [ ] [DiscardCardAction](#DiscardCardAction)
- [ ] [DrawCardAction](#DrawCardAction)
- [ ] [JoinAction](#JoinAction)
- [ ] [QuitAction](#QuitAction)
- [ ] [SayUnoAction](#SayUnoAction)
- [ ] [StartAction](#StartAction)

# Test specifications

## ChallengePlayerAction

### Valid challenge test

1. Arrange:
- A player (player1) has played a card with the WILD_FOUR symbol

2. Act:
- The next player (player2) calls challengePlayer(player1)

3. Assert:
- player1 loses the challenge if they have any other card they could have played (player1 draws 4)
- player1 wins the challenge if they didn't have any other card they could have played (player2 draws 6)

## DiscardCardAction

### Valid discard test

1. Arrange:
- Player's hand isn't empty
- It is the player's turn
- The selected card can be played

2. Act:
- Player calls discardCard(card)

3. Assert:
- Card gets added to the discard pile on the server
- Client got the correct PartialGameState from the server

### Invalid discard test (not the player's turn)

1. Arrange:
- Player's hand isn't empty
- It isn't the player's turn
- The selected card can be played

2. Act:
- Player calls discardCard(card)

3. Assert:
- Client gets an INVALID_ACTION response

### Invalid discard test (player hand is empty)

1. Arrange:
- Player's hand is empty
- It is the player's turn
- The selected card can be played

2. Act:
- Player calls discardCard(card)

3. Assert:
- Client gets an INVALID_ACTION response

### Invalid discard test (card cannot be played)

1. Arrange:
- Player's hand isn't empty
- It is the player's turn
- The selected card cannot be played

2. Act:
- Player calls discardCard(card)

3. Assert:
- Client gets an INVALID_ACTION response

## DrawCardAction

### Valid draw card

1. Arrange:
- It is the player's turn

2. Act:
- Player calls drawCard()

3. Assert:
- Top card gets removed from the draw pile
- The card gets added to the player's hand
- The client gets a PartialGameState response with the updated draw pile and hand

### Invalid draw card

1. Arrange:
- It isn't the player's turn

2. Act:
- Player calls drawCard()

3. Assert:
- Client gets an INVALID_ACTION response

## JoinAction

### Valid join

1. Arrange:
- The game isn't full (player count < 10)

2. Act:
- Player calls joinGame(address, port)

3. Assert:
- Player gets added to the players on the server
- Client gets a PartialGameState response with the updated player list

### Invalid join

1. Arrange:
- The game isn full (player count >= 10)

2. Act:
- Player calls joinGame(address, port)

3. Assert:
- Client gets an INVALID_ACTION response

## QuitAction

### Valid quit

1. Arrange:
- The player is in the game

2. Act:
- Player calls quitGame()

3. Assert:
- Player gets removed from the player list
- Player's hand gets added back to the draw pile
- Draw pile gets shuffled
- Clients get a PartialGameState response with the updated player list

### Invalid quit

1. Arrange:
- The player isn't in the game

2. Act:
- Player calls quitGame()

3. Assert:
- Client gets an INVALID_ACTION response

## SayUnoAction

### Valid say uno

1. Arrange:
- The player's hand size is 1
- It's the next player's turn

2. Act:
- Player calls sayUno()

3. Assert:
- Player's hasSaidUno status updates to true
- Client gets a PartialGameStateResponse with the updated player data

### Invalid say uno (hand size)

1. Arrange:
- The player's hand size isn't 1
- It's the next player's turn

2. Act:
- Player calls sayUno()

3. Assert:
- Client gets and INVALID_MOVE response

### Invalid say uno (too late)

1. Arrange:
- The player's hand size is 1
- It's the next next player's turn

2. Act:
- Player calls sayUno()

3. Assert:
- Client gets and INVALID_MOVE response

## StartAction

### Valid start action

1. Arrange:
- The player is the host

2. Act:
- Player calls startGame()

3. Assert:
- A new deck is created
- A card is added to the discard pile
- 7 cards get dealt to all players
- The dealt cards get removed from the draw pile
- The clients get a PartialGameResponse

### Inalid start action

1. Arrange:
- The player isn't the host

2. Act:
- Player calls startGame()

3. Assert:
- Client gets an INVALID_ACTION response