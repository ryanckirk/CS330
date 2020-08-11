#lang racket  ; change to "Determine language from source" in lower left corner
;------------------------------------------------------------------------------
; CS 330 Fall 2019 Programming Exercise 4 - Symbolic Differentiation
;
; author:  <RYAN KIRK>
;
; date:  <10/23/19>
;
;------------------------------------------------------------------------------

;------------------------------------------------------------------------------
; (variable? E) - Is E a variawble (single symbol)?
;
; PRECONDITIONS: E is a list or atom
; POSTCONDITIONS: returns #t or #f (no side-effects)
; EXAMPLE USE:  (variable? 'x) returns #t

(define (variable? x) (symbol? x))

;------------------------------------------------------------------------------
; (same-variable? V1 V2) - Are V1 and V2 same variable?
;
; PRECONDITIONS: V1 and V2 are lists and/or atoms
; POSTCONDITIONS:  returns #t if V1 and V2 are variables
;           and V1 = V2 (in the eqv? sense)
; EXAMPLE USE: (same-variable? 'x 'x) returns #t

(define (same-variable? v1 v2)
  (and (variable? v1) (variable? v2) (eq? v1 v2)))

;------------------------------------------------------------------------------
; (sum? E) - Is E a summation function call?
;
; PRECONDITIONS: E is a list or atom
; POSTCONDITIONS: returns #t if E is a list and first element is +
; EXAMPLE USE: (sum? '(+ x 5)) returns #t

(define (sum? E)
  (and (list? E) (eq? '+ (list-ref (list* E)0))))
  

;(define (listLength? A)
 ; (length(list (list*A))))
;
;(d;efine (returnList? E)
  ;(list-ref(list E)0))



;------------------------------------------------------------------------------
; (addend SUM) - Returns the first operand of the sum
;
; PRECONDITIONS: SUM is a sum list
; POSTCONDITIONS: returns the first operand: number, variable, 
;          or another operation
; EXAMPLE USE: (addend '(+ x 5)) returns 'x

(define (addend SUM)
  (list-ref (list* SUM)1))



;------------------------------------------------------------------------------
; (augend SUM) - returns the second operand of the sum
;
; PRECONDITIONS: SUM is a sum list
; POSTCONDITIONS: returns the second operand: number, variable,
;          or another operation
; EXAMPLE USE: (augend '(+ x 5)) returns 5

(define (augend SUM)
  (list-ref (list* SUM)2))



;------------------------------------------------------------------------------
; (=number? EXP NUM) - checks if EXP is a number and equal to NUM
;
; PRECONDITIONS: EXP is an expression list, NUM is a number
; POSTCONDITIONS: #t returned if EXP is a number and equal to NUM,
;         otherwise #f
; EXAMPLE USE: (=number? 5 5) returns #t

(define (=number? EXP NUM)
  
  (and (number? EXP) (equal? EXP NUM)))



;------------------------------------------------------------------------------
; (make-sum A1 A2) - returns a sum list for A1 + A2
;
; PRECONDITIONS: A1 and A2 are valid expressions
; POSTCONDITIONS: list expression (+ A1 A2) or reduced form
;        if either A1 or A2 is zero or both are numbers
; EXAMPLE USE: (make-sum 'x 5) returns (+ x 5)

(define (make-sum a1 a2)
  (cond ((=number? a1 0) a2)
        ((=number? a2 0) a1)
        ((and (number? a1) (number? a2)) (+ a1 a2))
        (else (list '+ a1 a2))))

(define (make-sub a1 a2)
  (cond ((and (number? a1) (number? a2)) (- a1 a2))
        (else (list '- a1 a2))))



;------------------------------------------------------------------------------
; (product? E) - Is E a product function call?
;
; PRECONDITIONS: E is a list or atom
; POSTCONDITIONS: returns #t if E is a list and first element is *
; EXAMPLE USE: (product? '(* 2 y)) returns #t

(define (product? E)
  (and (list? E) (eq? '* (list-ref (list* E)0))))


;------------------------------------------------------------------------------
; (multiplier PROD) - Returns the first operand of the product
;
; PRECONDITIONS: PROD is a product list
; POSTCONDITIONS: returns the first operand: number, variable, 
;          or another operation
; EXAMPLE USE: (multiplier '(* 2 y)) returns 2

(define (multiplier PROD)
  (list-ref (list* PROD)1))


;------------------------------------------------------------------------------
; (multiplicand PROD) - returns the second operand of the product
;
; PRECONDITIONS: PROD is a product list
; POSTCONDITIONS: returns the second operand: number, variable,
;          or another operation
; EXAMPLE USE: (multiplicand '(* 2 y)) returns 'y

(define (multiplicand PROD)
  (list-ref (list* PROD)2))



;------------------------------------------------------------------------------
; (make-product M1 M2) - returns a product list for M1 + M2
;
; PRECONDITIONS: M1 and M2 are valid expressions
; POSTCONDITIONS: list expression (+ M1 M2) or reduced
;              form for M1 or M2 equal to 0, 1, or both numbers
; EXAMPLE USE: (make-product 2 'y) returns (* 2 y)

(define (make-product m1 m2)
  (cond ((or (=number? m1 0) (=number? m2 0)) 0)
        ((=number? m1 1) m2)
        ((=number? m2 1) m1)
        ((and (number? m1) (number? m2)) (* m1 m2))
        (else (list '* m1 m2))))



;------------------------------------------------------------------------------
; (** BASE EXPONENT) - raises BASE to the EXPONENT
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: returns BASE raised to EXPONENT
; EXAMPLE USAGE:  (** 2 5) returns 32

(define (** BASE EXPONENT)
  (expt BASE EXPONENT))

;------------------------------------------------------------------------------
; (power? BASE EXPONENT) - checks to see if it is a power function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: returns BASE raised to EXPONENT
; EXAMPLE USAGE:  (** 2 5) returns 32

;(define (sum? E)
;  (and (list? E) (eq? '+ (list-ref (list* E)0))))


;(define (power? E)
;  (and (list? E) (eq? '** (list-ref (list* E)0))))
(define (power? EXP)
  (and (pair? EXP) (eq? (car EXP)'**)))

;----------------------------------------
(define (power-base E)
  (cadr E))
(define (power-exp E)
  (caddr E))

;------------------------------------------------------------------------------
; (quotient? BASE EXPONENT) - checks to see if it is a power function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: returns BASE raised to EXPONENT
; EXAMPLE USAGE:  (** 2 5) returns 32

(define (quotient? exp)
  (and (pair? exp) (eq? (car exp) '/)))


(define (numerator exp)
  (list-ref exp 1))

(define (denomenator exp)
  (list-ref exp 2))


;------------------------------



;------------------------------------------------------------------------------
; (sin? exp) - checks to see if it is a sin function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: #t or #f
; EXAMPLE USAGE:  


(define (sin? exp)
  (eq? (car exp) 'sin))



;------------------------------------------------------------------------------
; (cos? exp ) - checks to see if it is a cos function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: #t or #f
; EXAMPLE USAGE:  


(define (cos? exp)
  (eq? (car exp) 'sin))

;------------------------------------------------------------------------------
; (log? exp ) - checks to see if it is a log function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: #t or #f
; EXAMPLE USAGE:  

(define (log? exp)
  (eq? (car exp) 'log))

;------------------------------------------------------------------------------
; (expo? exp ) - checks to see if it is a exp function 
;
; PRECONDITIONS: BASE and EXPONENT are integers
; POSTCONDITIONS: #t or #f
; EXAMPLE USAGE:  

(define (expo? exp)
  (eq? (car exp) 'exp))




;------------------------------------------------------------------------------
; (make-power BASE EXPONENT) - checks to see if it is a power function 
;
; PRECONDITIONS: 
; POSTCONDITIONS:
; EXAMPLE USAGE:
;(define (make-power BASE EXPONENT)
 ; (cond ((or (=number? m1 0) (=number? m2 0)) 0)
  ;      ((=number? m1 1) 1)
   ;     ((=number? m2 1) m1)
    ;    ((and (number? m1) (number? m2)) (** m1 m2))
     ;   (else (list '** m1 m2))))

(define (make-power m1 m2)
  (cond ((=number? m1 0) 0)
        ((=number? m2 0) 1)
        ((=number? m2 1) m1)
        ((and (number? m1) (number? m2)) (** m1 m2))
        (else (list '** m1 m2))))

  ;(make-product (power-exp)
   ;             (list '** (power-base exp) (- (power-exp) 1))
    ;            (deriv (power-base exp) var)))

;------------------------------------------------------------------------------
; (reduce-power BASE EXPONENT) - checks to see if it is a power function
(define (reduce-power BASE EXPONENT)
  (- (list-ref (list* BASE) 2)1))
 



;------------------------------------------------------------------------------
; (make-quotient BASE EXPONENT) - checks to see if it is a power function 
;
; PRECONDITIONS: 
; POSTCONDITIONS:
; EXAMPLE USAGE:

(define (make-quotient m1 m2)
  (cond ((=number? m1 0) 0)
        ((=number? m2 0) error "not ablte to divide by a number that is zero")
        ((and (number? m1) (number? m2)) (/ m1 m2))
        (else (list '/ m1 m2))))

;------------------------------------------------------------------------------
; (make-sin- m1) - create sin function 
;
; PRECONDITIONS: 
; POSTCONDITIONS:
; EXAMPLE USAGE:

(define (make-sin m1)
  (cond ((number? m1) (sin m1))
        (else (list 'sin m1))))


;------------------------------------------------------------------------------
; (make-cos m1) - create cos function 
;
; PRECONDITIONS: 
; POSTCONDITIONS:
; EXAMPLE USAGE:

(define (make-cos m1)
  (cond ((number? m1) (cos m1))
        (else (list 'cos m1))))

    
     



;** FOR THE POWER RULE, YOU WILL NEED SOME ADDITIONAL HELPER FUNCTIONS **

(define (get-first exp)
  (list-ref exp 1))



;********* COMPLETE THE COMMENT BLOCKS FOR THE FOLLOWING *****************

; (deriv-variable EXP VAR) - returns symbolic derivative of a variable
;(define (deriv-variable EXP VAR)
  ;(


; (deriv-sum EXP VAR) - returns the symbolic derivative of a sum

(define (deriv-sum exp var)
  (make-sum (deriv (addend exp) var)
  (deriv (augend exp) var)))
  



; (deriv-product EXP VAR) - returns the symbolic derivative of a product

(define (deriv-product exp var)
  (make-sum
           (make-product (multiplier exp)
                         (deriv (multiplicand exp) var))
           (make-product (deriv (multiplier exp) var)
                         (multiplicand exp))))
  


; (deriv-const EXP VAR) -returns a the symolic derivative of a constant
(define (deriv-const exp var)
  (if (same-variable? exp var) 1 0))

; (deriv-power exp var) -
(define (deriv-power exp var)
  (append (make-power exp var) (reduce-power)))

  





;------------------------------------------------------------------------------
; (deriv EXP VAR) - returns expression list that is the derivative
;          of EXP with respect to VAR
;
; PRECONDITIONS:  EXP is a list representing a linear expression,
;                 VAR is a variable
; POSTCONDITIONS: returns the derivative of EXP with respect to VAR
;          returns an error message if improperly formatted expression
; EXAMPLE USE: (deriv '(+ (* 3 x) (* a x)) 'x) returns (+ 3 a)
;--------deriv-Sum-------


 (define (deriv exp var)
   ;--------deriv-const-------
  (cond ((number? exp) 0)

        ;--------deriv-variable-------
        ((variable? exp)
         (deriv-const exp var))
          ;--------deriv-Sum-------
        ((sum? exp)
         (deriv-sum exp var ))
        ;--------deriv-product-------
        ((product? exp)
         (deriv-product exp var))
        ;--------deriv-power-------
        ((power? exp)
         (make-product (power-exp exp)
                       (make-product (make-product (power-base exp) (- (power-exp ) 1))
                                     (deriv (power-base exp) var))))

        ;--------deriv-product-------
        ((product? exp)
         (make-sum
         (make-product (multiplier exp)
                       (deriv (multiplicand exp) var))
         (make-product (deriv (multiplier exp) var)
                       (multiplicand exp))))
         
        ;-------deriv-quotient-------
        ((quotient? exp)
         (make-quotient (make-sub (make-product (denomenator exp)
                                               (deriv (numerator exp) var))
                                 (make-product (numerator exp)
                                               (deriv (denomenator exp) var)))
                        (make-power (denomenator exp) 2)))
                                            
                                                   
         ;-------deriv-sin---
        ((sin? exp)
         (make-product (make-cos (get-first exp)) (deriv (get-first exp) var)))

         ;-------deriv-cos---
        ((cos? exp)
         (make-product (make-sub 0 (make-sin (get-first exp)))
                       (deriv (get-first exp) var)))

         ;-------deriv-lg---
        ((log? exp)
         (make-product (make-quotient 1 (get-first exp))
                       (deriv (get-first exp) var)))

        ;-------deriv-exp---
        ((expo? exp)
         (make-product (make-quotient 1 (get-first exp))
                       (deriv (get-first exp) var)))
        
        

        ;-------deriv-power---
        ((expo? exp)
         (make-product exp (deriv (get-first exp) var)))
         
           
        (else
         (error "unknown expression type -- DERIV" exp))))











 ;---------------------------------------------------------------------------------



;------------------------------------------------------------------------------
; (replace VAR VALUE EXP) - replaces variable VAR with value VALUE 
;                           in expression EXP
;
; PRECONDITIONS: VAR is a variable, VALUE is numeric value, 
;                EXP is an expression list
; POSTCONDITIONS: returns the EXP expression with VAR replaced by VALUE
; EXAMPLE USE: (replace 'x 4 '(+ x (* 3 x))) returns (+ 4 (* 3 4))

 (define (replace var value exp)
    ((eq? (first exp) var) (list-set exp (index-of exp var) value) (replace last)))
   



;------------------------------------------------------------------------------
; (eval-deriv EXP VAR VALUE) - computes the derivative of EXP
;            with respect to VAR and evaluates at VAR = VALUE
;
; PRECONDITIONS: VAR is a variable, VALUE is numeric value, 
;                EXP is an expression list
; POSTCONDITIONS: returns the value of the derivative of EXP expression with
;           respect to VAR and then evaluated at VALUE
; EXAMPLE USE: (eval-deriv '(* (+ x 3) x) 'x 5) returns 13
;
; HINT:  Make use of the (replace ...) function above

(define (eval-deriv exp var value)
  (deriv (replace var value exp) var))


;******* ADD APPROPRIATE COMMENT BLOCKS TO THE FUNCTIONS BELOW **********

; (infix-sum? EXP) - checks if EXP is an infix sum

(define (infix-sum? EXP)
  (if (and (= (length EXP) 3) (eq? (cadr EXP) '+)) #t #f))

; (infix-product? EXP) - checks if EXP is an infix product

(define (infix-product? E)
  (and (list? E) (eq? '* (list-ref (list* E)0))))

; (infix-power? EXP) - checks if EXP is an infix power

(define (infix-power? EXP)
  (and (pair? EXP) (eq? (car EXP)'**)))

; (infix-quotient? EXP) - checks if EXP is an infix quotient
 

  
; (infix-2-prefix EXP) - converts expression EXP from infix to prefix
;
; *** ADD CODE TO HANDLE POWERS, QUOTIENTS, COSINES, AND LOG EXPRESSIONS ***

(define (infix-2-prefix EXP)
  (cond
    ((or (null? EXP) (variable? EXP) (number? EXP)) EXP)
    ((and (list? EXP) (= (length EXP) 1)) (infix-2-prefix (car EXP))) ; handles (x) from cos, sin, ...
    ((or (infix-sum? EXP) (infix-product? EXP) )
         (list (cadr EXP) (infix-2-prefix (car EXP)) (infix-2-prefix (caddr EXP))))
    ((or (sin? EXP) )
         (list (car EXP) (infix-2-prefix (cadr EXP))))
    (else
         (error "Could not convert prefix expression to infix."))))

; (prefix-2-infix EXP) - converts expression EXP from prefix to infix

;(define (prefix-2-infix exp)
;  (append (list-ref(list exp)1) (list-ref(list exp)0) (list-ref(listt exp)2)))
;
; *** ADD CODE TO HANDLE POWERS, QUOTIENTS, COSINES, AND LOG EXPRESSIONS ***

(define (prefix-2-infix EXP)
  (cond 
    ((or (null? EXP) (variable? EXP) (number? EXP)) EXP)
    ((or (sum? EXP) (product? EXP) )
         (list (prefix-2-infix (cadr EXP)) (car EXP) (prefix-2-infix (caddr EXP))))
    ((or (sin? EXP)  )
         (list (car EXP) (list (cadr EXP))))
    (else
         (error "Could not convert prefix expression to infix."))))

; (derivative EXP VAR) - computes symbolic derivative of infix expression EXP wrt VAR
;                        providing the results in infix format
;
; HINT:  CONVERT INFIX TO PREFIX, USE (deriv ...), THEN CONVERT BACK TO INFIX

(define (derivative exp var)
  (prefix-2-infix(derivative(infix-2-prefix exp) var)))




  
; (eval-derivative EXP VAR VALUE) - evaluates the derivative of infix expression EXP wrt VAR
;                                   at VAR = VALUE
; HINT:  CONVERT INFIX TO PREFIX, THEN USE (replace ...) AND (eval ...)

(define (eval-derivative exp var value)
  (eval-deriv (replace var value (infix-2-prefix exp)) var value))



