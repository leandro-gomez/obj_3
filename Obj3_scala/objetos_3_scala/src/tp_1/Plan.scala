package tp_1

import tp_1_comunications.Comunication

abstract case class Plan {
	def cost(comunication: Comunication, table: Table): Double
}