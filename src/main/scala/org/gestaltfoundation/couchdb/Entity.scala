package org.gestaltfoundation.couchdb

import net.liftweb.json._
import net.liftweb.json.Serialization.write

/** Convenience features for case classes representing db objects
  */
class Entity ( private val type_str: String ) {
    var id: String = _
    private val type_json = "{ \"Type\": \"%s\" }".format( type_str )
    implicit val formats = DefaultFormats

    def json (): String = {
        val json = parse ( write ( this ) ) merge parse ( type_json )
        write ( json )
    }
}

// vim: set ts=4 sw=4 et:
