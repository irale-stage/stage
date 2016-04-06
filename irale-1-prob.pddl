;; irale-1-prob.pddl
(define (problem color-blocksworld-1-prob)
	(:domain color-blocksworld-1)
	(:objects FLOOR - cFLOOR C_8 C_9 C_4 C_5 C_6 C_7 C_1 C_2 C_3 - var)
	(:init (clear1 C_1 )(clear1 C_5 )(clear1 C_3 )(clear1 C_4 )(clear1 C_7 )(on2 C_1 FLOOR )(on2 C_2 FLOOR )(on2 C_3 FLOOR )(on2 C_4 FLOOR )(on2 C_5 C_2 )(on2 C_6 FLOOR )(on2 C_7 C_6 )(color2 C_1 C_8 )(color2 C_2 C_9 )(color2 C_3 C_8 )(color2 C_4 C_9 )(color2 C_5 C_8 )(color2 C_6 C_8 )(color2 C_7 C_8 ))
	(:goal (and (clear1 C_1 )(clear1 C_6 )(clear1 C_7 )(on2 C_2 FLOOR )(on2 C_4 FLOOR )(on2 C_5 C_2 )(on2 C_7 FLOOR )(on2 C_3 C_5 )(on2 C_6 C_3 )(on2 C_1 C_4 )(color2 C_1 C_8 )(color2 C_2 C_9 )(color2 C_3 C_8 )(color2 C_5 C_8 )(color2 C_6 C_8 )(color2 C_7 C_8 )(color2 C_4 C_8 ))))
