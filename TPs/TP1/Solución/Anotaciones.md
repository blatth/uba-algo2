**agregarBloque(b: Secuencia(Transacción))** [b = bloque = secuencia de transacciones]

## Precondiciones:

- cantidad de transacciones válidas: 0 < |b| ≤ 50 -> cada bloque contiene entre 1 y 50 transacciones
- transacciones con IDs ordenados dentro del bloque: (∀i ∈ [1..#transacciones(b)]) id(transacciones(b)[i−1]) < id(transacciones(b)[i])
- cada transacción tiene:
	- id_transacción: entero positivo
    - id_comprador ≠ id_vendedor, ambos enteros positivos
    - monto: entero positivo
- como máximo una transacción de creación por bloque: si existe, debe ser la primera (posición 0) y cumplir: id_comprador = 0 y id_vendedor ≠ 0. solo permitida si el total emitido < 3000 ->  esTransacciónDeCreación(t) <=> comprador(t) = 0 ∧ vendedor(t) ≠ 0
- el comprador tiene dinero suficiente en todas las transacciones que no sean de creación?: se verifica que cada id_comprador (≠ 0) tenga al menos el monto correspondiente en su balance antes de gastar

## Postcondiciones:

- se agrega un nuevo bloque a la cadena, con identificador igual a id del último bloque + 1.
- los saldos de los users se actualizan:
	- se suma al saldo del vendedor el monto de cada transacción.
	- se resta del saldo del comprador el monto (si id_comprador ≠ 0).
- si hay una transacción de creación, se emite nueva moneda:
	- se incrementa el total emitido en la cantidad que corresponda
	- se suma el mismo monto al saldo del vendedor
- condiciones más generales del sistema:
	- nadie puede gastar más de lo que tiene
	- no se supera el límite de emisión de 3000 unidades
	- se preserva la secuencia de bloques de forma consecutiva

---

## Generador: 

**transacción: nat x nat x nat x nat -> Transacción** [(id, id_comprador, id_vendedor, monto)]

---

## Observadores:

- **idBloque: Bloque -> nat)**
- **transacciones: Bloque -> Secuencia(Transacción)**

### Ejemplo de uso:

- b = bloque(3, t1, t2, t3)

- transacciones(b) = < 3, t1, t2, t3 >, donde < t1, t2, t3 > es una secuencia de 4 transacciones (por ejemplo t1 = transacción(0, 0, 5, 100) y así)

