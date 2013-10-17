package practico_1

import java.sql.{ Array => _, _ }
import java.util.Date
import java.util.Calendar
import sun.security.util.Length

object Empresa_Telefonica {   
  abstract case class Comunicacion(nroDestino: Int, mes: Date){
  	def es_larga(): Boolean  	
  	def costo(tabla:Tabla): Double
  }
  abstract case class Llamada(override val nroDestino: Int, duracion: Int, override val mes: Date) extends Comunicacion(nroDestino, mes){
  	def es_larga(): Boolean = {
  	  duracion > 60 * 5  		
  	}  	
  	
  	def costo_del_minuto(tabla:Tabla): Double
  	
  	def costo(tabla:Tabla): Double = {
  		costo_segun_tiempo(costo_del_minuto(tabla))
  	}
  	
  	def costo_segun_tiempo(costo_del_minuto: Double): Double = {
      if (duracion < 60)
        return costo_del_minuto
      else {
        var partial = costo_del_minuto * (duracion / 60) + (duracion / 10) * 10
        if (duracion % 10 > 0)
          partial += 10
        return partial
      }
    }
  	  	
  }

  case class Local(override val nroDestino: Int, override val duracion: Int, override val mes: Date) extends Llamada(nroDestino, duracion, mes) {
    def costo_del_minuto(tabla:Tabla):Double = {
    	60
    }
  }
  case class LargaDistancia(override val nroDestino: Int, override val duracion: Int, origen: String, destino: String, override val mes: Date) extends Llamada(nroDestino, duracion, mes){
    def costo_del_minuto(tabla:Tabla):Double = {
    	tabla costo_desde_hasta(origen, destino)
    }    
  }
  case class Mensaje(override val nroDestino: Int, longitud: Int, override val mes: Date) extends Comunicacion(nroDestino, mes){
  	def es_larga():Boolean = {
  		longitud > 140
  	}
  	
  	def costo(tabla:Tabla): Double  = {	        
       if (longitud < 140)
         return 12.0
       else {
         var partial = longitud / 140 * 12
         if (longitud % 140 > 0)
           partial += 12
         return partial
       } 
  	}
        
  }

  case class Cliente(name: String) {

    var comunicaciones = List[Comunicacion]()
    
    def costo_del_mes(mes:Date, tabla:Tabla): Double = {
  		comunicaciones.foldLeft(0.0)((a,b)=> a+b.costo(tabla))
  	}
  	
    def addComunication(comunicacion: Comunicacion) {
      this.comunicaciones = comunicacion :: this.comunicaciones
    }
    
    def llamadas(): List[Llamada] = {
      comunicaciones collect { case x: Llamada => x }    	
    }
    
    def locales(): List[Local] = {
    	comunicaciones collect { case x: Local => x }
    }
    
    def largas_distancias():List[LargaDistancia] = {
    	comunicaciones collect {case x: LargaDistancia => x}
    }
    
    def mensajes: List[Mensaje] = {
    	comunicaciones collect {case x: Mensaje => x}
    }
    
    def llamadas_del_mes(mes:Date): List[Llamada] = {
  		this.llamadas filter (_.mes.getMonth == mes.getMonth)
    }
    
    def comunicaciones_largas(mes: Date): List[Comunicacion] = {
      this.comunicaciones filter(_ es_larga )
          
    } 
    def count_comunicaciones_largas(mes:Date):Int = {
      	comunicaciones_largas(mes) length      	
      }
    
    def comunicaciones_del_mes(mes: Date): List[Comunicacion] = {
    	comunicaciones filter(_.mes.getMonth == mes.getMonth)
    }
    
    def comunicaciones_del_dia(mes: Date): List[Comunicacion] = {
      comunicaciones_del_mes(mes) filter(_.mes.getDate == mes.getDate)
    }
    
    def minutos_del_mes(mes:Date):Double = {
      llamadas_del_mes(mes).foldLeft(0.0)((suma, comunicacion) => suma + comunicacion.duracion)
    }

  }

  class Tabla() {
    // TODO: This design could be better!

    var tabla = collection.mutable.HashMap[String, collection.mutable.HashMap[String, Double]]()
    var precioDefault = 2.5
    var default = collection.mutable.HashMap(
      "default" -> precioDefault)
    def costo_desde_hasta(origen: String, destino: String): Double = {
      tabla getOrElse(origen, default) getOrElse(destino, precioDefault)
    }

    def asegurar_origen(origen: String): collection.mutable.HashMap[String, Double] = {
      if (!(tabla contains(origen))) {
        tabla(origen) = collection.mutable.HashMap[String, Double]()
      }
      tabla.get(origen) get
    }

    def agregar_origen(origen: String) = asegurar_origen(origen)

    def desfinir_costo_desde_hasta(origen: String, destino: String, costo: Double) {
      asegurar_origen(origen)(destino) = costo
    }

  }
  def main(strings: Array[String]){
      var empresa = new  Empresa()
      var leandro = Cliente("leandro")
      var julian = Cliente("julian")
      var ivan = Cliente("ivan")
      var daniel = Cliente("daniel")
      
      empresa add_client(leandro)
      empresa add_client(julian)
      empresa add_client(ivan)
      empresa add_client(daniel)
      
      //Ivan
      //Locales = 0
      //Larga Distancias = 1 (normal)
      //Mensajes = 20 (15 normales y 5 largos)
      var hora_modelo = Calendar getInstance() getTime()
      (0 until 16) foreach((num:Int) => {
      	var hora = Calendar getInstance() getTime()
      	hora setHours(hora_modelo.getHours() + num)
      	ivan addComunication(Mensaje(12345678,20, hora))
      	}
      )
      (0 until 6) foreach((num:Int) => {
      	var hora = Calendar getInstance() getTime()
      	hora setHours(hora_modelo.getHours() + num)      	
      	ivan addComunication(Mensaje(12345678,200, hora))
      	}
      )
      println("minutos de "+ ivan.name + ": " + empresa.minutos_del_mes(ivan,hora_modelo))           
            
  }

  class Empresa {
    var clientes = List[Cliente]()
    var tabla = new Tabla()

    def add_client(cliente: Cliente) {
      clientes = cliente :: clientes
    }

    // II
    //Ejercicio 1
    // 1)
    def minutos_del_mes(cliente: Cliente, mes: Date): Double = {
      cliente minutos_del_mes(mes)
    }
    // 2)
    def costo_del_mes(cliente: Cliente, mes: Date): Double =
      cliente costo_del_mes(mes,tabla)
    // 3)          
    def comunicaciones_largas(cliente:Cliente, mes:Date):Double = {
    	cliente count_comunicaciones_largas(mes)
    }     
    // 4)    
    def destinos_distantes(cliente: Cliente, mes: Date) {
      cliente.largas_distancias map {_ destino}
    }
    // 5)        
    def mas_comunicaciones(cliente: Cliente, mes: Date): Date = {
      var more = 0
      var this_month = Calendar getInstance() getTime()
      var next_month = Calendar getInstance() getTime()
      this_month setYear(mes getYear())
      this_month setMonth(mes getMonth())
      this_month setDate(1)
      next_month
      next_month setYear(mes getYear())
      next_month setMonth(mes.getMonth() + 1)
      next_month setDate(1)
      var date = this_month
      while (this_month.before(next_month)) {
        var this_month_count = num_comunicaiones_del_dia(cliente, this_month)
        if (this_month_count > more) {
          date = this_month
          more = this_month_count
        }
      }
      date
    }

    // Ejercicio 2
    //1)
    def cliente_hablador(mes: Date): Cliente = bestClientBy(mes, minutos_del_mes)
    //2)
    def mejor_cliente(mes: Date): Cliente = bestClientBy(mes, costo_del_mes)
    //3)    
    def cliente_con_mas_comunicaciones_largas(mes:Date): Cliente = bestClientBy(mes,num_comunicaiones_del_dia)    

    // III
    // Bonus    

    // TODO: BONUS!    

    //Funciones auxiliares

    def bestClientBy(mes: Date, function: (Cliente, Date) => Double): Cliente = {
      clientes.foldLeft(clientes first)(
        (a: Cliente, b: Cliente) => {
          if (function(a, mes) < function(b, mes)) {
            b
          } else {
            a
          }
        })
    }           
    
    def costo(comunicacion:Comunicacion,cliente:Cliente): Double = {
    	comunicacion costo(tabla)
    }
    
    def num_comunicaiones_del_dia(cliente: Cliente, mes: Date): Int = {
      cliente comunicaciones_del_dia(mes) length
    }   

  }

}