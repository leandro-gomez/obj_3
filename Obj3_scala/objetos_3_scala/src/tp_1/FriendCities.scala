package tp_1

import tp_1_comunications.Comunication

import tp_1_comunications.LongDistance

case class FriendCities(city: String) extends Plan {
	override def cost(comunication: Comunication, table: Table): Double = comunication match {
		case comunicacion: LongDistance => if (comunicacion.destino.equals(city)) { 0.0 } else { comunicacion.minuteCost(table) }
		case _ => comunication.cost(table)
	}
}