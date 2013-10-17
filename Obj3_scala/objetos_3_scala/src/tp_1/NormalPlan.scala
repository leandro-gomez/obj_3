package tp_1

import tp_1_comunications.Comunication

case class NormalPlan extends Plan {
	override def cost(comunication: Comunication, table: Table): Double = {
		comunication.cost(table)
	}
}