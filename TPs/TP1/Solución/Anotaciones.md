**agregarBloque(b: Secuencia(Transacción))** [b = bloque = secuencia de transacciones]

## Precondiciones:

- cantidad de transacciones válidas: 0 < |b| ≤ 50 -> cada bloque contiene entre 1 y 50 transacciones
- transacciones con IDs ordenados dentro del bloque: (∀i ∈ [1..#transacciones(b)])(id(transacciones(b)[i−1]) < id(transacciones(b)[i])
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

- **idBloque: Bloque -> nat**
- **transacciones: Bloque -> Secuencia(Transacción)**

### Ejemplo de uso:

- b = bloque(3, t1, t2, t3)

- transacciones(b) = < 3, t1, t2, t3 >, donde < t1, t2, t3 > es una secuencia de 4 transacciones (por ejemplo t1 = transacción(0, 0, 5, 100) y así)

---

## Rec TADs
- **[Apunte de TADs](https://github.com/blatth/uba-algo2/blob/main/Apuntes/TADs.pdf)**

Cada función debe indicar la precondición y la postcondición (requiere y asegura). Como ya indicamos, los requiere y asegura van a hacer referencia a los **observadores** del TAD. Para referirnos al **observador** x de la instancia t usamos la notación t.x. Para hablar del valor inicial de un parámetro de tipo inout usamos metavariables, refiriendo al tipo entero, no al **observador**, es decir, T_0.x, no t.X_0. Las metavariables deben declararse en el requiere.
Para que la especificación sea completa, tenemos que describir el estado de todos los **observadores** al _salir_ de la operación. Recordemos que la implementación final del TAD solo debe respetar la especificación y puede modificar cualquier cosas que no se defina.

---

	[RENOMBRE DE TIPOS]

Transacción = struct {
  id_transaccion: ℤ
  id_comprador: ℤ
  id_vendedor: ℤ
  monto: ℤ
}

Bloque = struct {
  id: ℤ
  transacciones: seq<Transacción>
}

TAD $Berretacoin {
  obs cadenaBloques: seq<Bloque>
  obs tenedores: dict<Z, Z>
  obs totalCreado: Z

	[CONSTRUCTOR]

  proc berretacoinNuevo(): $Berretacoin{
    asegura {
    res.cadenaBloques = ⟨⟩ ∧
    res.tenedores = {} ∧
    res.totalCreado = 0
    	}
	}

  proc agregarBloque(inout bc: $Berretacoin, in trnsc: seq<Transacción>){
    requiere {
	cadenaBloques = cadenaBloques₀ ∧
	totalCreado = totalCreado₀ ∧
	tenedores = tenedores₀
		}
	requiere {
    |trnsc| ≤ 50 ∧ --> cada bloque puede tener como máximo 50 transacciones
    ∀ t ∈ trnsc: t.monto > 0 ∧ t.id_comprador ≠ t.id_vendedor ∧ --> no se permiten transacciones con montos negativos ni cero ∧ el comprador y el vendedor deben ser distintos
    (trnsc ≠ [] → (trnsc[0].id_comprador = 0 ↔ bc.totalCreado < 3000)) ∧ --> controla si se puede incluir una transacción de creación: si trnsc no está vacía, la primera transacción es de creación y verifica que todavía se pueden emitir monedas
    ∀ t ∈ trnsc: t.id_comprador ≠ 0 → t.id_comprador ∈ bc.tenedores ∧ bc.tenedores[t.id_comprador] ≥ t.monto --> controla que ningún comprador (salvo el 0 que es de creación) intente gastar más de lo que tiene: si id ≠ 0, t.id_comprador debe estar en el diccionario de tenedores y Y su saldo debe ser mayor o igual al monto de la transacción
    	}
    asegura { ---> se agrega el bloque ∧ se actualiza el total creado ∧ se actualizan los saldos 
    bc.cadenaBloques = append(cadenaBloques₀, nuevoBloque) ∧
    bc.totalCreado = min(totalCreado₀ + montoCreado, 3000) ∧
    bc.tenedores = actualizarSaldos(tenedores₀, trnsc)
    	}
	}
}