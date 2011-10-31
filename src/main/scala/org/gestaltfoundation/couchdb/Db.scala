package org.gestaltfoundation.couchdb

import net.liftweb.json._
import net.liftweb.json.Serialization.write

case class DatabaseInfo ( val db_name: String, val doc_count: Int, 
    val doc_del_count: Int, val update_seq: Int, val purge_seq: Int,
    val compact_running: Boolean, val disk_size: Int, val data_size: Int,
    val instance_start_time: String, val disk_format_version: Int, 
    val committed_update_seq: Int
)

class Db ( val host: String, val port: Int, val name: String ) extends Base {
    val base_url = "http://%s:%d/%s".format ( host, port, name )
    private var db_info: DatabaseInfo = _
    private var db_exists = true

    var jsonresp = get ( base_url )
    if ( jsonresp.isError ) db_exists = false
    else db_info = jsonresp.as[DatabaseInfo]

    def exists: Boolean = db_exists
    def info = db_info

    def save[T<: AnyRef] ( obj: (T) ): Option[String] = {
        implicit val formats = DefaultFormats
        var json = write ( obj )
        val resp = post ( base_url, json )
        if ( resp.isError ) {
            None
        } else {
            Some(resp.as[NewSuccess].id)
        }
    }
}

// vim: set ts=4 sw=4 :et
