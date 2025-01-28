# Tetris2048
The Tetris 2048 game we were going to design required us to create numbers on an empty 4x4
grid, and we did it. These tetrominoes progress exponentially if the numbers written on them are
mathematically the same. To keep the game constantly up to date, we created a gameplay loop
and allowed players to move these tetrominoes around the 2D playing field (down or left) as they
wished. In the logic of the game, if a row is full of tetrominoes, then the row is completely
removed and all tetrominoes above it are moved to the next row. According to the logic of the
game, if there is a bivalent tetromino, these tetrominoes are combined. If a tetromino touches the
tetromino at the top of the board, the game is over. In other words, 2048 must be reached to win,
and the game continues after it reaches 2048. The numbers get bigger. However, if the 4x4 grid is
filled with tiles and there is no moving space, the player loses the game.
<img width="635" alt="Screenshot 2025-01-28 at 1 25 36 PM" src="https://github.com/user-attachments/assets/4dd6bc00-6b50-469a-bb7a-f5e91f360c5c" />
