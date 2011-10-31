package org.gestaltfoundation.couchdb

import net.liftweb.json.{DefaultFormats,parse}

class Response ( val raw_response: String ) {
    implicit val formats = DefaultFormats
    val json = parse ( raw_response )
    
    def as[T] ( implicit m: Manifest[T] ): T = {
        json.extract[T]
    }

    def isError: Boolean = {
        val err = json.extractOrElse ( Error("NONE", "") )
        if ( err.error == "NONE" ) false else true
    }
}

// vim: set ts=4 sw=4 et:
