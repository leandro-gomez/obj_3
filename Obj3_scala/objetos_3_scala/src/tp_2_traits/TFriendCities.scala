package tp_2_traits

import tp_1.{ Table }
import tp_1_comunications.{ Comunication, LongDistance }

trait TFriendCities {
	var cities: List[String] = List[String]()
	def costo(comunicacion: Comunication, tabla: Table): Double = comunicacion match {
		case comunicacion: LongDistance => costoLargaDistancia(comunicacion, tabla)
		case _ => comunicacion.cost(tabla)
	}

	def addCity(city: String) {
		if (cities.length == 5) {
			cities = city :: cities.tail
		} else {
			cities = (city :: (cities.reverse.tail)) reverse
		}
	}

	def costoLargaDistancia(comunicacion: LongDistance, tabla: Table): Double = {
		if (cities.contains(comunicacion.destino)) { 0.0 } else { comunicacion.minuteCost(tabla) }
	}

}