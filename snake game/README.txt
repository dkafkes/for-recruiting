=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 76417927
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collections -- I used Lists to model aspects of game state such as the ArrayList
  of Points that made up the Snake's body, or the LinkedLists of previous body lengths
  and head positions that enabled me to revert the Snake back to its previous position
  and length after consuming a Transport Back Five Blob or read/write the body part Points
  as x-coordinate and y-coordinate positions as integers to a file.

  2. File I/O -- The "Save Game Button" automatically makes the game reload once you
  reopen the game. The relevant parts of game state I chose to save were the score, Snake's
  length, type of Blob, Blob position, and Snake body part points. These were all broken up
  into integers. I wrote out and read back the integers in UTF-8, which was different
  from the reader/writer implementation in HW 8.

  3. Dynamic Dispatch -- I implemented this in the abstract method of desiredSnakeMovement.
  Within the six blob types I created that extended this abstract class. This method
  updated the keystroke velocity, the spacing between snake joints, the ordering of the 
  Snake joints, and the position of the Snake's head, resulting in varied Snake motions
  depending on the last blob consumed.

  4. Testable Components -- I was able to test many different aspects of the Collections
  I implemented as a part of game state. I was able to test Snake growth as well as what
  happens when a Teleport Blob was consumed (to see if it jumps to a random new location)
  or a Transport Back Five Blob (to see if snake goes back to where it was five moves ago.
  I was also able check to see that the Snake's movement algorithm corresponded correctly
  to the correct updated positions being saved in the other Linked List. I was also able
  to test that the random generator of new blob positions does not land a blob on top of the
  snake, which is the only disallowed position within the grid of allowable positions and
  that the Snake dies once it goes out of bounds.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Snake Class -- All methods relating to moving the snake, drawing the snake, and keeping track
  of the Snake's state (alive or dead), and information necessary for the fancier blobs:
  transportation (know previous five positions-- position updated after each keystroke),
  teleportation (if the snake had been already teleported, I indicated that-- else it just
  flitted randomly across the JPanel), and the switch-up blob (also indicated whether or not
  the ArrayList reversal of BodyParts had occurred).
  
  Abstract Blob Class -- Abstract class that generates the random location of every blob (and gives
  the ability to change it's location should it be on top of the blob), keeps track of whether or
  not it has been eaten, and how it should be drawn.
  
  Implementations of Blob Class -- This includes NormalBlob, SmushBlob, SpreadOutBlob, SwitchUpBlob,
  SwitchUpBlob, TeleportBlob, TransportBackFiveBlob.
  
  In these classes, I set the type of the blob, the color, and also how each caused the Snake
  to move after they'd been consumed. The user would only know the blob type by color, which is 
  why this game is Crazy Snake and not regular Snake.
  	* Red Blobs were NormalBlobs that did nothing special to the Snake's movement.
	* Gray Blobs were SwitchUpBlobs that reversed the Snake's head and the way the
	player controlled them on the keypad.
	* White Blobs were SmushBlobs that caused the points that the Snake was made of
	to smush together and consequently slowed down the snake.
	* Magenta Blobs were SpreadOutBlobs that caused the points that the Snake was
	made of to spread out.
	* Blue Blobs were TeleportBlobs that teleported the Snake to a random new location
	on the board.
	* Yellow Blobs were TransportBackFiveBlobs that transported the Snake back to the
	location where it was five keystrokes ago and reverted the Snake back to its length
	five keystrokes ago.
	
	GameMethods Class -- This class contained all the methods for interactions between
	the Snake, the Blob, and the Board. For example, GameMethods contains methods for
	what happens once a snake eats a blob, my random blob type generator and checker
	to ensure it is not on top of the Snake, and a method to check if the Snake is
	still in-bounds.
	
	GameEngine Class -- This class created the JPanel where the game would run and
	defined the tick method and combined repaint method of what was supposed to
	happen when the game was run. Basically, I implemented different GameMethod
	methods in the GameEngine Class depending on certain situations: like the snake
	dying, incrementing score, repainting with new blobs after the Snake had consumed
	the previous, and ensuring the right causesSnaketoMoveLike method was being called. 
	Additionally, once I added file IO part of this project, I also had the relevant
	things I was saving be inputs into the creation of the JPanel.
	
	GameRun Class -- This is the runnable part of my code where all the GameMethods
	are run on the GameEngine that is added as a panel. This class includes my
	start up screen and what happens when the game is Saved and Reset.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I struggled the most with the actual implementation of the Swing Panels and the File
  IO part. The low point was when I had added two panels to my JFrame and only one
  was being added and I could not figure out why, but eventually I figured it out.
  I also had a lot of trouble with my buttons. I feel like modeling the game state
  was the easiest part of this assignment. Getting the user to interact with it was
  a lot harder.
  
- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  
  I think there is a lot of redundancy in terms of how I'm modeling the Snake and Blobs
  in GameMethods and in their own classes. If I were to do it again, I would put less
  methods in the actual Snake and Blob classes themselves and just do the bulk of it
  in GameMethods. Also, I feel like it might not have been right to have GameEngine
  and GameMethods be separate, so maybe I'd just make GameEngine next time that includes
  all of the Methods I needed.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
