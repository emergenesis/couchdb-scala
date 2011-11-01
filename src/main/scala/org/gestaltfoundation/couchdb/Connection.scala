package org.gestaltfoundation.couchdb

/** A communications exception */
case class ConnectionException ( val msg: String ) extends Exception

/** Represents the current connection to a CouchDB instance */
case class ConnectionInfo ( val couchdb: String, val version: String )

/** Represents a connection to a CouchDB instance 
  *
  * @param host The host of CouchDB
  * @param port The CouchDB port number
  * @throws ConnectionException When there was a communications problem.
  */
class Connection ( host: String, port: Int ) extends Base {

    /** The calculated URL of the server */
    val server_url = "http://%s:%d".format ( host, port )

    /** The version of the CouchDB running on server */
    val couch_version = connect

    /** Connect to the CouchDB instance 
      *
      * @return The CouchDB version
      * @throws ConnectionException
      */
    def connect: String = {
        var info = get ( server_url ).as[ConnectionInfo]
        info.version
    }

    /** Return a Db object from its name
      *
      * @param dbname The name of the database to retrieve
      * @throws ConnectionException
      */
    def apply ( dbname: String ): Db = {
        new Db ( host, port, dbname )
    }

    /** Get a list of all databases
      *
      * @return A list of all database names
      * @throws ConnectionException
      */
    def listDatabases : List[String] = {
        get ( server_url + "/_all_dbs" ).as[List[String]]
    }

    /** Create a new databases
      *
      * @return The database object
      * @throws ConnectionException
      */
    def createDatabase ( name: String ): Db = {
        val resp = put ( server_url + "/" + name ).as[QuerySuccess]
        new Db ( host, port, name )
    }

    /** Delete a database
      *
      * @return Whether or not the operation succeeded
      * @throws ConnectionException
      */
    def deleteDatabase ( name: String ) : Boolean = {
        val resp = delete ( server_url + "/" + name ).as[QuerySuccess]
        resp.ok
    }
}

// vim: set ts=4 sw=4 et:
