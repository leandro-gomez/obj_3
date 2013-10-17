package tp_1

import tp_1_comunications.Call

import tp_1_comunications.Comunication

import tp_1_comunications.Message

case class FriendNumbers(friendNumbers: List[Integer]) extends Plan {

	if (friendNumbers.length > 5) {
		throw new RuntimeException("List too long")
	}

	override def cost(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: Call => if (friendNumbers.contains(comunicacion.destinationNumber)) { 0.0 } else { comunicacion.cost(tabla) }
		case comunicacion: Message => comunicacion.cost(tabla)
	}
}