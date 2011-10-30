package org.gestaltfoundation.couchdb

import net.liftweb.json.{DefaultFormats,parse}

class Response ( val response: String ) {
    implicit val formats = DefaultFormats
    
    def as[T] ( implicit m: Manifest[T] ): T = {
        parse ( response ).extract[T]
    }
}

// vim: set ts=4 sw=4 et:
