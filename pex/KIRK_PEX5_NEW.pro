

% Airport Rush - Can you make it home for thanksgiving??

% Break Time puts you in the hot seat of a cadet trying to make it home for thanksgiving

main:- airport_rush.       

airport_rush:-
  init_dynamic_facts,     

  write('AIRPORT RUSH - Can you make it home for thanksgiving?'),nl,
  write('Copyright (C) Captain Kirk Game Studios. 2019'),nl,
  
 
  write('The winter weather is closing in'),nl,
  write('What will you do to make sure you make it home for thanksgiving??'),nl,
  write('Find your boarding pass in time and you get to go home'),nl,
  write('Game Commands:'),nl,
  write('   go to [ROOM]          (ex. go to the office)'),nl,
  write('   look                  (ex. look)'),nl,
  write('   look in something     (ex. look in the desk)'),nl,
  write('   take [ITEM]           (ex. take the apple)'),nl,
  write('   drop [ITEM]           (ex. drop the apple)'),nl,
  write('   eat  [ITEM]           (ex. eat the apple)'),nl,
  write('   turn [ITEM] on        (ex. turn on the light)'),nl,
  write('   inventory             (ex. inventory)'),nl,
  nl,
  nl,
  write('Hit any key to continue.'),
  get0(_),
  write('Type "help" if you need more help on mechanics.'),nl,
  write('Type "hint" if you want a big hint.'),nl,
  write('Type "quit" if you give up.'),nl,
  nl,
  write('Enjoy the hunt.'),nl,

  look,                   
  command_loop.



command_loop:-
  repeat,
  get_command(X),
  do(X),
  (boardingpassfound; X == quit).



do(goto(X)):-goto(X),!.
do(bthelp):-bthelp,!.
do(hint):-hint,!.
do(inventory):-inventory,!.
do(take(X)):-take(X),!.
do(drop(X)):-drop(X),!.
do(eat(X)):-eat(X),!.
do(look):-look,!.
do(turn_on(X)):-turn_on(X),!.
do(turn_off(X)):-turn_off(X),!.
do(look_in(X)):-look_in(X),!.
do(quit):-quit,!.



boardingpassfound:-
  have(boardingpass),        
  write('Congratulations, you saved your thanksgiving break.'),nl,
  write('Now you can rest in your own bed with your leave doggo.'),nl,nl.

quit:-
  write('Giving up?  It''s going to be a long week on the hill with General Edmonster'),nl.
 


bthelp:-
 write('Game Commands:'),nl,
  write('   go to [ROOM]          (ex. go to the office)'),nl,
  write('   look                  (ex. look)'),nl,
  write('   look in something     (ex. look in the desk)'),nl,
  write('   take [ITEM]           (ex. take the apple)'),nl,
  write('   drop [ITEM]           (ex. drop the apple)'),nl,
  write('   eat  [ITEM]           (ex. eat the apple)'),nl,
  write('   turn [ITEM] on        (ex. turn on the light)'),nl,
  write('   inventory             (ex. inventory)'),nl,
  nl,
  nl,
  write('Hit any key to continue.'),
  get0(_),
  look.

hint:-
  write('You need to get to the longue to relax after a stressful journey, and you can''t unless you have the right paperwork'),nl,
  look.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INITIALIZE%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%All the different rooms. Some are dead ends%%%%%%%%%%%
room(office).
room(dorm).
room(parking).
room(squadron).
room(bathroom).
room(interstate).
room(airport).
room(tsa).
room(unitedclub).
room(bar).


%%%%%%%%%%%%%%%%%%%%%Doors represent connections between rooms%%%%%%%%%%%%%%%%%%%%%%%%

door(dorm,squadron).
door(squadron,bathroom).
door(squadron,parking).
door(squadron,office).
door(unitedclub,bar).



door(interstate,parking).
door(interstate,airport).
door(tsa,airport).
door(unitedclub,airport).

connect(X,Y):-
  door(X,Y).
connect(X,Y):-
  door(Y,X).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%Intial starting location of items and the elusive boarding pass%%%%%%%%%%%%%%%%%
init_dynamic_facts:-
  assertz(location(desk,office)),
  assertz(location(orders,desk)),
  assertz(location(formten,desk)),
  assertz(location(hair,bathroom)),
  assertz(location(showercaddy,bathroom)),
  assertz(location(keys,dorm)),
  assertz(location(suitcase,dorm)),
  assertz(location(car,parking)),
  assertz(location(softpretzel,tsa)),
  assertz(location('sterile gloves',tsa)),
  
  assertz(location(boardingpass,bar)),
  assertz(location(freefood,unitedclub)),
  assertz(location(cqdesk,squadron)),
  assertz(location(boxnasty,cqdesk)),

  
  assertz(here(dorm)),
  assertz(turned_off(car)).

%%%%%%%%%%%%%%%%%Furniture means that items can be located inside of it%%%%%%%%%%%%%%%%%%%%%%%%%
furniture(desk).

furniture(cqDesk).

%%%%%%%%%%%%%%%%%%%%%%%%Food items can be eaten and taste either good or bad%%%%%%%%%%%%%%%
edible(softpretzel).
tastes_yuchy(boxnasty).
tastes_yuchy(hair).

%%%%%%%% COMMANDS %%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%Go to a room%%%%%%%%%%%%%%%%%%
goto(Room):-
  can_go(Room), %%First looks to make sure the rooms are connected                
  puzzle(goto(Room)),	%%Checks puzzle logic of if you can enter the interstate
  puzzleairport(goto(Room)),	%%Checks puzzle logic if you can enter the airport
  moveto(Room),  %%If all three above are true for the given room, you may enter the room               
  look.
goto(_):- look.

%%Checks to see if the rooms are connect. Looks at initalized values
can_go(Room):-                  
  here(Here),                   
  connect(Here,Room),!.
can_go(Room):-
  respond(['You can''t get to ',Room,' from here']),fail.

%%If you can move, moves cadet into the new room 
moveto(Room):-                  
  retract(here(_)),             
  asserta(here(Room)).


%%Function to see what is in the room that the cadet is currently located in
look:-
  here(Here),
  respond(['You are in the ',Here]),
  write('You can see the following things:'),nl,
  list_things(Here),
  write('You can go to the following rooms:'),nl,
  list_connections(Here).

%%Lists the items in a given place
list_things(Place):-
  location(X,Place),
  tab(2),write(X),nl,
  fail.
list_things(_).

%%Lists the rooms connected to the current room
list_connections(Place):-
  connect(Place,X),
  tab(2),write(X),nl,
  fail.
list_connections(_).


%%Looks inside items that are considered furniture
look_in(Thing):-
  location(_,Thing),               
  write('The '),write(Thing),write(' contains:'),nl,
  list_things(Thing).
look_in(Thing):-
  respond(['There is nothing in the ',Thing]).


%%Function that allows items to be added to inventory
%%All items that are not furniture can be added to inventory

take(Thing):-
  is_here(Thing),	%%If the item is located in the current room
  is_takable(Thing),	%%If the item is not furniture
  puzzlekeys(take(Thing)),	%%Checks the puzzle logic if the car can be used

  move(Thing,have),	%%moves item to inventory
  respond(['You now have the ',Thing]).

%%If an item is in the room
is_here(Thing):- 
  here(Here),
  contains(Thing,Here),!.          
is_here(Thing):-
  %%False condition
  respond(['There is no ',Thing,' here']),
  fail.

contains(Thing,Here):-             
  location(Thing,Here).            
contains(Thing,Here):-
  location(Thing,X),
  contains(X,Here).
  %%Used for items that contain other items. Ex furniture

%%Furniture is not takeable
is_takable(Thing):-                
  furniture(Thing),
  respond(['You can''t pick up a ',Thing]),
  !,fail.
is_takable(_).                     

move(Thing,have):-
  retract(location(Thing,_)),      
  asserta(have(Thing)).            

% drop - allows the player to transfer an invetory item to the current room

drop(Thing):-
  have(Thing),                     
  here(Here),	%%Current room                     
  retract(have(Thing)),
  asserta(location(Thing,Here)).
drop(Thing):-
  respond(['You don''t have the ',Thing]).



%%eat function
eat(Thing):-
  have(Thing),
  eat2(Thing).
eat(Thing):-
  %%can't eat what you don't have
  respond(['You don''t have the ',Thing]).
  
eat2(Thing):-
  edible(Thing),
  retract(have(Thing)),
  %%You no longer have the item
  respond(['That ',Thing,' was good. Use this new energy to make it to your flight']).
eat2(Thing):-
  tastes_yuchy(Thing),
  respond(['You really are a cadet if you tried to eat ',Thing]).
eat2(Thing):-
  %%Items that you can not eat
  respond(['You can''t eat a ',Thing]).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%INVENTORY%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

inventory:-
  have(X),                         
  write('You have: '),nl,
  list_possessions.
inventory:-
  write('You have nothing'),nl.

list_possessions:-
  have(X),
  tab(2),write(X),nl,
  fail.
list_possessions.


%%Logic involved in turning on the car

turn_on(Thing):-
  have(Thing),
  turn_on2(Thing).
turn_on(Thing):-
  respond(['You don''t have the ',Thing]).

turn_on2(Thing):-
  turned_on(Thing),
  respond([Thing,' is already on']).
turn_on2(Thing):-
  turned_off(Thing),
  retract(turned_off(Thing)),
  asserta(turned_on(Thing)),
  respond([Thing,' turned on']).
turn_on2(Thing):-
  respond(['You can''t turn a ',Thing,' on']).



%%%%%%%%%%%%%%%%%%%%%%%PUZZLE LOGIC%%%%%%%%%%%%%%%%%%%%%

%%These functions control the puzzles of the game
%%These two puzzles include having car keys and having the right paperwork
puzzle(goto(interstate)):-
  have(car),
  turned_on(car),!.
puzzle(goto(interstate)):-
  write('You can''t go to the interstate because you dont have the car'),nl,
  !,fail.
puzzle(_).

puzzlekeys(take(car)):-
  have(keys),!.
puzzlekeys(take(car)):-
  write('You need keys to take the car'),nl,
  !,fail.
puzzlekeys(_).

puzzlei(take(car)):-
  have(keys),!.
puzzlei(take(car)):-
  write('you need keys in order to drive the car'),n1,
  
  !,fail.
puzzlei(_).

puzzleairport(goto(airport)):-
  have(orders),!.
puzzleairport(goto(airport)):-
  write('You can''t go to the airport becase you do not have your orders yet'),nl,
  !,fail.
puzzleairport(_).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Natural language processing%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%This comes from the Nani Search example%%%%%%%%%%%%%%
respond([]):-
  write('.'),nl,nl.
respond([H|T]):-
  write(H),
  respond(T).



get_command(C):-
  readlist(L),        
  command(X,L,[]),    
  C =.. X,!.          
get_command(_):-
  respond(['I don''t understand, try again or type help']),fail.



command([Pred,Arg]) --> verb(Type,Pred),nounphrase(Type,Arg).
command([Pred]) --> verb(intran,Pred).
command([goto,Arg]) --> noun(go_place,Arg).



verb(go_place,goto) --> go_verb.
verb(thing,V) --> tran_verb(V).
verb(intran,V) --> intran_verb(V).

go_verb --> [go].
go_verb --> [go,to].
go_verb --> [g].

tran_verb(take) --> [take].
tran_verb(take) --> [pick,up].
tran_verb(drop) --> [drop].
tran_verb(drop) --> [put].
tran_verb(drop) --> [put,down].
tran_verb(eat) --> [eat].
tran_verb(turn_on) --> [turn,on].
tran_verb(turn_on) --> [switch,on].
tran_verb(turn_off) --> [turn,off].
tran_verb(look_in) --> [look,in].
tran_verb(look_in) --> [look].
tran_verb(look_in) --> [open].

intran_verb(inventory) --> [inventory].
intran_verb(inventory) --> [i].
intran_verb(look) --> [look].
intran_verb(look) --> [look,around].
intran_verb(look) --> [l].
intran_verb(quit) --> [quit].
intran_verb(quit) --> [exit].
intran_verb(quit) --> [end].
intran_verb(quit) --> [bye].
intran_verb(bthelp) --> [help].
intran_verb(hint) --> [hint].



nounphrase(Type,Noun) --> det,noun(Type,Noun).
nounphrase(Type,Noun) --> noun(Type,Noun).

det --> [the].
det --> [a].



noun(go_place,R) --> [R], {room(R)}.
noun(go_place,'dining room') --> [dining,room].

noun(thing,T) --> [T], {location(T,_)}.
noun(thing,T) --> [T], {have(T)}.
noun(thing,flashlight) --> [flash,light].
noun(thing,'washing machine') --> [washing,machine].
noun(thing,'dirty clothes') --> [dirty,clothes].



noun(thing,light) --> [X,light], {room(X)}.
noun(thing,flashlight) --> [light], {have(flashlight)}.
noun(thing,light) --> [light].



readlist(L):-
  write('> '),
  read_word_list(L).

read_word_list([W|Ws]) :-
  get0(C),
  readword(C, W, C1),       
  restsent(C1, Ws), !.      

restsent(C,[]) :- lastword(C), !. 
restsent(C,[W1|Ws]) :-
  readword(C,W1,C1),        
  restsent(C1,Ws).

readword(C,W,C1) :-         
  single_char(C),           
  !, 
  name(W, [C]),             
  get0(C1).
readword(C, W, C1) :-
  is_num(C),                
  !,
  number_word(C, W, C1, _). 
readword(C,W,C2) :-         
  in_word(C, NewC),         
  get0(C1),                  
  restword(C1,Cs,C2),            
  name(W, [NewC|Cs]).       
readword(C,W,C2) :-         
  get0(C1),       
  readword(C1,W,C2).        

restword(C, [NewC|Cs], C2) :-
  in_word(C, NewC),
  get0(C1),
  restword(C1, Cs, C2).
restword(C, [], C).


single_char(0',).
single_char(0';).
single_char(0':).
single_char(0'?).
single_char(0'!).
single_char(0'.).


in_word(C, C) :- C >= 0'a, C =< 0'z.
in_word(C, L) :- C >= 0'A, C =< 0'Z, L is C + 32.
in_word(0'',0'').
in_word(0'-,0'-).



number_word(C, W, C1, Pow10) :- 
  is_num(C),
  !,
  get0(C2),
  number_word(C2, W1, C1, P10),
  Pow10 is P10 * 10,
  W is integer(((C - 0'0) * Pow10) + W1).
number_word(C, 0, C, 0.1).


is_num(C) :-
  C =< 0'9,
  C >= 0'0.



lastword(10).   
lastword(0'.).
lastword(0'!).
lastword(0'?).