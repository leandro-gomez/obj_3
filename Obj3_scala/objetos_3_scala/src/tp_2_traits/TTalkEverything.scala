package tp_2_traits

import tp_1.{ Table }
import tp_1_comunications.{ Comunication, Message, Call, LocalCall, LongDistance }
import java.util.Calendar
import java.util.Date

trait TTalkEverything {
	def costo(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: Message => comunicacion.cost(tabla)
		case comunicacion: Call => costoLlamada(comunicacion, tabla)
	}
	def costoLlamada(llamada: Call, tabla: Table): Double = {
		if (llamada.date.getDay() == Calendar.SATURDAY || llamada.date.getDay() == Calendar.SUNDAY) {
			if (llamada.duration < 5) { 0.0 } else {
				llamada match {
					case LocalCall(nroDestino: Int, duracion: Int, mes: Date) =>
						LocalCall(nroDestino, duracion - 5, mes).cost(tabla)
					case LongDistance(nroDestino: Int, duracion: Int, origen: String, destino: String, mes: Date) =>
						LongDistance(nroDestino, duracion - 5, origen, destino, mes).cost(tabla)

				}
			}

		} else {
			llamada.cost(tabla)
		}

	}
}