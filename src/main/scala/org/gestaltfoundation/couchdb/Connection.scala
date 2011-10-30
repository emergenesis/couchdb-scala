package org.gestaltfoundation.couchdb

import dispatch._

case class ConnectionException ( val msg: String ) extends Exception
case class ConnectionInfo ( val couchdb: String, val version: String )

class Connection ( host: String, port: Int ) extends Base {

    val couch_version = connect

    def connect: String = {
        val h = new Http
        
        try {
            var resp = h ( url ( "http://%s:%d/".format ( host, port ) ) as_str )
            var info = parseJson( resp ).extract[ConnectionInfo]
            info.version
        } catch {
            case e => throw ConnectionException ( "Could not connect to CouchDB server at %s:%d; exception was %s".format ( host, port, e.getClass.getName ) )
        }
    }

    def apply ( db: String ) {
        // get a Db object
    }
}

// vim: set ts=4 sw=4 et:
