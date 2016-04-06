;; irale-1-domain.pddl

(define (domain color-blocksworld-1)
	(:requirements :adl)
	(:types var
	 cFLOOR
	)
	(:predicates (clear1 ?A )(on2 ?A ?B )(color2 ?A ?Y ))
	(:action R1
		:parameters ( ?A - var  ?B - var  ?FLOOR - cFLOOR)
		:precondition (and (clear1 ?A )(on2 ?A ?B ))
		:effect (and(clear1 ?B )(on2 ?A ?FLOOR )( not(on2 ?A ?B )) ))

	(:action R3
		:parameters ( ?A - var  ?B - var  ?C - var  ?X - var )
		:precondition (and (clear1 ?A )(clear1 ?B )(on2 ?A ?C )(color2 ?A ?X )(color2 ?B ?X ))
		:effect (and(clear1 ?C )(on2 ?A ?B )( not(clear1 ?B )) ( not(on2 ?A ?C )) ))

	(:action R2
		:parameters ( ?A - var  ?B - var  ?FLOOR - cFLOOR ?X - var )
		:precondition (and (clear1 ?A )(clear1 ?B )(on2 ?A ?FLOOR )(color2 ?A ?X )(color2 ?B ?X ))
		:effect (and(on2 ?A ?B )( not(clear1 ?B )) ( not(on2 ?A ?FLOOR )) ))

	(:action R4
		:parameters ( ?A - var  ?B - var  ?Y - var  ?X - var )
		:precondition (and (clear1 ?A )(clear1 ?B )(color2 ?A ?X )(color2 ?B ?Y ))
		:effect (and(color2 ?A ?Y )( not(color2 ?A ?X )) ))
)