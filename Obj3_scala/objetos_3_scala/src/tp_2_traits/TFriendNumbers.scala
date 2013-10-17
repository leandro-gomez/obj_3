package tp_2_traits

import tp_1.{ Table }
import tp_1_comunications.{ Message, Call, Comunication }

trait TFriendNumbers {

	var friendNumbers: List[Integer] = List[Integer]()

	def costo(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: Call => costoLlamada(comunicacion, tabla)
		case comunicacion: Message => comunicacion.cost(tabla)
	}

	def addNumber(number: Integer) {
		if (friendNumbers.length == 5) {
			friendNumbers = number :: friendNumbers.tail
		} else {
			friendNumbers = (number :: (friendNumbers.reverse.tail)) reverse
		}
	}

	def costoLlamada(comunicacion: Comunication, tabla: Table): Double = {
		if (friendNumbers.contains(comunicacion.destinationNumber)) { 0.0 } else { comunicacion.cost(tabla) }
	}

}