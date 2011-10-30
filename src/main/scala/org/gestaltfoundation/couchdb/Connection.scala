package org.gestaltfoundation.couchdb

case class ConnectionException ( val msg: String ) extends Exception
case class ConnectionInfo ( val couchdb: String, val version: String )

class Connection ( host: String, port: Int ) extends Base {

    val server_url = "http://%s:%d".format ( host, port )
    val couch_version = connect

    def connect: String = {
        try {
            var info = getResponse ( server_url ).as[ConnectionInfo]
            info.version
        } catch {
            case e => throw ConnectionException ( "Could not connect to CouchDB server at %s:%d; exception was %s".format ( host, port, e.getClass.getName ) )
        }
    }

    def apply ( db: String ) {
        // get a Db object
    }

    def listDatabases : List[String] = {
        try {
            getResponse ( server_url + "/_all_dbs" ).as[List[String]]
        } catch {
            case e => throw ConnectionException ( "Communication with CouchDB was unsuccessful. Exception was: %s".format( e.getClass.getName ) )
        }
    }
}

// vim: set ts=4 sw=4 et:
