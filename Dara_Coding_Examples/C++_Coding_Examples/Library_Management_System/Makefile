OBJ = main.o PLScontrol.o View.o Book.o Patron.o ChildPatron.o AdultPatron.o Storage.o PDeque.o PatronFactory.o ChildFactory.o AdultFactory.o

a1:	$(OBJ) Server.o
	g++ -o a1 $(OBJ) Server.o

main.o:	main.cc types.h
	g++ -c main.cc

PLScontrol.o:	PLScontrol.cc PLScontrol.h types.h
	g++ -c PLScontrol.cc

View.o:	View.cc View.h types.h
	g++ -c View.cc

Book.o:	Book.cc Book.h types.h
	g++ -c Book.cc

Patron.o:	Patron.cc Patron.h types.h
	g++ -c Patron.cc

ChildPatron.o:	ChildPatron.cc ChildPatron.h types.h
	g++ -c ChildPatron.cc

AdultPatron.o:	AdultPatron.cc AdultPatron.h types.h
	g++ -c AdultPatron.cc

Storage.o: Storage.cc storage.h types.h Server.h
	g++ -c Storage.cc

PDeque.o: PDeque.cc PDeque.h types.h
	g++ -c PDeque.cc

PatronFactory.o: PatronFactory.cc PatronFactory.h types.h
	g++ -c PatronFactory.cc

ChildFactory.o:	ChildFactory.cc ChildFactory.h types.h
	g++ -c ChildFactory.cc

AdultFactroy.o:	AdultFactroy.cc AdultFactory.h types.h
	g++ -c AdultFactroy.cc


clean:
	rm -f a1 $(OBJ)
