package tp2_planes

import tp_2_traits.TNormalPlane
import tp_1_comunications.{ LocalCall, LongDistance, Call, Comunication }
import tp_1.{ Table }

class NormalPlane extends TNormalPlane {

	def costoLocal(llamada: LocalCall, tabla: Table): Double = {
		defaultCosto(llamada, tabla)
	}

	def costoLargaDistancia(llamada: LongDistance, tabla: Table): Double = {
		defaultCosto(llamada, tabla)
	}

	def costoLlamada(comunicacion: Call, tabla: Table): Double = {
		defaultCosto(comunicacion, tabla)
	}

	def defaultCosto(comunicacion: Comunication, tabla: Table): Double = {
		comunicacion.cost(tabla)
	}

}