package org.gestaltfoundation.couchdb

case class ConnectionException ( val msg: String ) extends Exception
case class ConnectionInfo ( val couchdb: String, val version: String )
case class QuerySuccess ( val ok: Boolean )

class Connection ( host: String, port: Int ) extends Base {

    val server_url = "http://%s:%d".format ( host, port )
    val couch_version = connect

    def connect: String = {
        try {
            var info = get ( server_url ).as[ConnectionInfo]
            info.version
        } catch {
            case e => throw ConnectionException ( 
                "Could not connect to CouchDB server at %s:%d; exception was %s".
                format ( host, port, e.getClass.getName ) 
            )
        }
    }

    def apply ( db: String ) {
        // get a Db object
    }

    def listDatabases : List[String] = {
        try {
            get ( server_url + "/_all_dbs" ).as[List[String]]
        } catch {
            case e => throw ConnectionException ( 
                "Communication with CouchDB was unsuccessful. Exception was: %s".
                format( e.getClass.getName ) 
            )
        }
    }

    def createDatabase ( name: String ) : Boolean = {
        try {
            val resp = put ( server_url + "/" + name ).as[QuerySuccess]
            resp.ok
        } catch {
            case e => false
        }
    }

    def deleteDatabase ( name: String ) : Boolean = {
        try {
            val resp = delete ( server_url + "/" + name ).as[QuerySuccess]
            resp.ok
        } catch {
            case e => false
        }
    }
}

// vim: set ts=4 sw=4 et:
