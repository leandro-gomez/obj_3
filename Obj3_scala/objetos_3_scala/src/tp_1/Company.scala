package tp_1

import java.util.Date
import java.util.Calendar
import tp_1_comunications.Comunication

class Company {
	var clients = List[Client]()
	var tabla = new Table()

	def addClient(client: Client) {
		clients = client :: clients
	}

	// II
	//Ejercicio 1
	// 1)
	def minutesOfMonth(cliente: Client, date: Date): Double = {
		cliente minutesOfMonth (date)
	}
	// 2)
	def costOfMonth(cliente: Client, date: Date): Double =
		cliente costByMonth (date, tabla)
	// 3)
	def LongComunications(cliente: Client, date: Date): Double = {
		cliente longComunicationsCallsCount (date)
	}
	// 4)
	def distantDestination(cliente: Client, date: Date) {
		cliente.longDistanceCalls map { _ destino }
	}
	// 5)
	def mostComunicationsDays(client: Client, month: Date): Date = {
		var comunicationCountRecord = 0
		var thisMonth = Calendar getInstance () getTime ()
		var nextMonth = Calendar getInstance () getTime ()
		thisMonth setYear (month getYear ())
		thisMonth setMonth (month getMonth ())
		thisMonth setDate (1)
		nextMonth
		nextMonth setYear (month getYear ())
		nextMonth setMonth (month.getMonth() + 1)
		nextMonth setDate (1)
		var date = thisMonth
		while (thisMonth.before(nextMonth)) {
			{
				var this_month_count = comunicationsOfDay(client, thisMonth)
				if (this_month_count > comunicationCountRecord) {
					date = thisMonth
					comunicationCountRecord = this_month_count
				}
			}
		}
		date
	}

	// Ejercicio 2
	//1)
	def talkativeClient(date: Date): Client = bestClientBy(date, minutesOfMonth)
	//2)
	def bestClient(date: Date): Client = bestClientBy(date, costOfMonth)
	//3)
	def clientWithMoreLongComunication(date: Date): Client = bestClientBy(date, comunicationsOfDay)

	// III
	// Bonus

	// TODO: BONUS!

	//Funciones auxiliares

	def bestClientBy(date: Date, function: (Client, Date) => Double): Client = {
		clients.foldLeft(clients first)(
			(a: Client, b: Client) => {
				if (function(a, date) < function(b, date)) {
					b
				} else {
					a
				}
			})
	}

	def cost(comunicacion: Comunication, cliente: Client): Double = {
		comunicacion cost (tabla)
	}

	def comunicationsOfDay(cliente: Client, date: Date): Int = {
		cliente comunicationsByDay (date) length
	}

}