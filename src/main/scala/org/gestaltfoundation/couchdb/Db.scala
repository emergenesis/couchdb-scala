package org.gestaltfoundation.couchdb

import net.liftweb.json._
import net.liftweb.json.Serialization.write

/** A container for information about a CouchDB database
  */
case class DatabaseInfo ( val db_name: String, val doc_count: Int, 
    val doc_del_count: Int, val update_seq: Int, val purge_seq: Int,
    val compact_running: Boolean, val disk_size: Int, val data_size: Int,
    val instance_start_time: String, val disk_format_version: Int, 
    val committed_update_seq: Int
)

/** Represents a database in CouchDB
  */
class Db ( val host: String, val port: Int, val name: String ) extends Base {
    val base_url = "http://%s:%d/%s".format ( host, port, name )
    private var db_info: DatabaseInfo = _
    private var db_exists = true

    var jsonresp = get ( base_url )
    if ( jsonresp.isError ) db_exists = false
    else db_info = jsonresp.as[DatabaseInfo]

    /** Does the database exist?
      *
      * @return True if the database exists, else false
      */
    def exists: Boolean = db_exists
    
    /** An object containing the db's info
      *
      * @return The db info
      * @see DatabaseInfo
      */
    def info = db_info

    /** Save an object from a case class to the database
      *
      * @tparam T The case class of the object
      * @param obj The object to save
      * @return True if successful, else false
      * 
      * @todo Add ability to update if ID is present
      */
    def save[T<: Entity] ( obj: (T) ): Boolean = {
        implicit val formats = DefaultFormats
        val resp = post ( base_url, obj.json )
        if ( resp.isError ) {
            false
        } else {
            val res = resp.as[NewSuccess]
            obj.id = res.id
            obj.revision = res.rev
            true
        }
    }

    /** Destroy an object that is already in the database
      *
      * @tparam T The case class of the object
      * @param obj The object to destroy
      * @return True if successful, else false
      */
    def destroy[T<: Entity] ( obj: (T) ): Boolean = {
        if ( ! obj.id.isEmpty && ! obj.revision.isEmpty ) {
            val resp = delete ( base_url + "/" + obj.id + "?rev=" + obj.revision ).as[QuerySuccess]
            if ( resp.ok ) {
                obj.id = ""
                obj.revision = ""
                true
            } else false
        } else false
    }
}

// vim: set ts=4 sw=4 :et
