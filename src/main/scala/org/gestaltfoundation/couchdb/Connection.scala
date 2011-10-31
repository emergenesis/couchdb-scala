package org.gestaltfoundation.couchdb

case class ConnectionException ( val msg: String ) extends Exception
case class ConnectionInfo ( val couchdb: String, val version: String )
case class QuerySuccess ( val ok: Boolean )

class Connection ( host: String, port: Int ) extends Base {

    val server_url = "http://%s:%d".format ( host, port )
    val couch_version = connect

    def connect: String = {
        var info = get ( server_url ).as[ConnectionInfo]
        info.version
    }

    def apply ( dbname: String ): Db = {
        new Db ( host, port, dbname )
    }

    def listDatabases : List[String] = {
        get ( server_url + "/_all_dbs" ).as[List[String]]
    }

    def createDatabase ( name: String ) = {
        val resp = put ( server_url + "/" + name ).as[QuerySuccess]
        new Db ( host, port, name )
    }

    def deleteDatabase ( name: String ) : Boolean = {
        val resp = delete ( server_url + "/" + name ).as[QuerySuccess]
        resp.ok
    }
}

// vim: set ts=4 sw=4 et:
