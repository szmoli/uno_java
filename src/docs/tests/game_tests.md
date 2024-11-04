# Action card tests

- [ ] [Draw 2](#draw-2)
- [ ] [Skip](#skip)
- [ ] [Reverse](#reverse)
- [ ] [Wild](#wild)
- [ ] [Wild draw 4](#wild-draw-4)

## Draw 2

1. Arrange:
- player has a draw 2 in their hand
- the draw 2's color or symbol matches the top card in the discard pile

2. Act:
- player plays their draw 2 card

3. Assert:
- the next player has drawn 2 cards
- the game selects the next next player

## Skip

1. Arrange:
- the player has a skip card
- the card can be played

2. Act:
- player plays their skip card

3. Assert
- the game selects the next next player

## Reverse

1. Arrange:
- the player has a reverse card
- the card can be played

2. Act:
- the player plays their reverse card

3. Assert:
- the player order gets reversed
- the correct player gets selected by the new order

## Wild

1. Arrange:
- the player has a wild card
- the card can be played

2. Act:
- the player plays their wild card

3. Assert:
- the top cards color is the color specified by the player

## Wild draw 4

1. Arrange:
- the player has a wild draw 4 card
- the card can be played

2. Act:
- the player plays their wild draw 4 card

3. Assert:
- the top cards color is the color specified by the player
- the next player draws 4 cards
- the game selects the next next player