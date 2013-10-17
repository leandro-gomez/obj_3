package tp_1

class Table() {
	// TODO: This design could be better!

	var table = collection.mutable.HashMap[String, collection.mutable.HashMap[String, Double]]()
	var defaultPrice = 2.5
	var default = collection.mutable.HashMap(
		"default" -> defaultPrice)
	def costo_desde_hasta(from: String, to: String): Double = {
		table getOrElse (from, default) getOrElse (to, defaultPrice)
	}

	def ensureOrigin(from: String): collection.mutable.HashMap[String, Double] = {
		if (!(table contains (from))) {
			table(from) = collection.mutable.HashMap[String, Double]()
		}
		table.get(from) get
	}

	def addOrigin(origen: String) = ensureOrigin(origen)

	def setCostFromTo(from: String, to: String, cost: Double) {
		ensureOrigin(from)(to) = cost
	}

}