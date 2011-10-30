package org.gestaltfoundation.couchdb
import net.liftweb.json.{DefaultFormats,parse}

trait Base {
    implicit val formats = DefaultFormats
    def parseJson ( json: String ) = parse ( json )
}

// vim: set ts=4 sw=4 et:
