package tp_2_traits

import tp_1.{ Table }
import tp_1_comunications.{ LocalCall, Comunication, Call, LongDistance }

trait TNormalPlane extends {
	def costo(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: LocalCall => costoLocal(comunicacion, tabla)
		case comunicacion: LongDistance => costoLargaDistancia(comunicacion, tabla)
		case comunicacion: Call => costoLlamada(comunicacion, tabla)
		case _ => comunicacion.cost(tabla)
	}

	def costoLocal(llamada: LocalCall, tabla: Table): Double

	def costoLargaDistancia(llamada: LongDistance, tabla: Table): Double

	def costoLlamada(comunicacion: Call, tabla: Table): Double

}