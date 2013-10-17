package tp_2_traits

import tp_1.{ Table }
import tp_1_comunications.{ Comunication, LongDistance }
import java.util.Date
import java.util.Calendar

trait TInternationalPrepayment extends TNormalPlane {
	var minutos: Integer = 30;
	var currentMonth: Date = Calendar getInstance () getTime ();

	override def costo(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: LongDistance => costoLargaDistancia(comunicacion, tabla)
		case _ => { super.costo(comunicacion, tabla) }
	}

	override def costoLargaDistancia(comunicacion: LongDistance, tabla: Table): Double = {
		if ((comunicacion date) equals currentMonth) {
			if ((comunicacion duration) > minutos) {
				var min_aux = (comunicacion duration) - minutos
				this.minutos = 0
				super.costo(LongDistance(comunicacion destinationNumber, min_aux, comunicacion origen, comunicacion destino, comunicacion date), tabla)
			} else {
				var min_aux = 0
				this.minutos -= comunicacion.duration
				super.costo(LongDistance(comunicacion destinationNumber, min_aux, comunicacion origen, comunicacion destino, comunicacion date), tabla)
			}
		} else {
			this.currentMonth = comunicacion.date
			this.costo(comunicacion, tabla)
		}

	}
}

