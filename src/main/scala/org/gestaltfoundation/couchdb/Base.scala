package org.gestaltfoundation.couchdb

import dispatch._

/** A unsuccessful server response representing some error */
case class Error ( val error: String, val reason: String )

/** A successful server response to a basic POST request */
case class NewSuccess ( ok: Boolean, id: String, rev: String )

/** A successful server response to a basic PUT request */
case class QuerySuccess ( val ok: Boolean )

/** Contains HTTP method utility functions for use throughout couchdb-scala
  * classes.
  */ 
trait Base {

    /** Performs a GET operation to the provided URL
      * 
      * @param urlstring The URL to GET
      * @return The HTTP response to the request
      * @throws ConnectionException When a communication issue arises
      */
    def get ( urlstring: String ): Response = {
        try {
            val http = new Http
            val resp = http ( url ( urlstring ) as_str )
            http.shutdown
            new Response ( resp )
        } catch {
            case e: StatusCode => new Response ( e.contents )
            case e => throw ConnectionException (
                "Could not connect to CouchDB server at %s; exception was %s".
                format ( urlstring, e.getClass.getName ) 
            )
        }
    }

    /** Performs a PUT operation on the provided URL
      * 
      * @param urlstring The URL to PUT
      * @return The HTTP response to the request
      * @throws ConnectionException When a communication issue arises
      */
    def put ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).PUT.as_str )
        http.shutdown
        new Response ( resp )
    }

    /** Performs a DELETE operation on the provided URL
      * 
      * @param urlstring The URL to DELETE
      * @return The HTTP response to the request
      * @throws ConnectionException When a communication issue arises
      */
    def delete ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).DELETE.as_str )
        http.shutdown
        new Response ( resp )
    }

    /** Performs a POST operation to the provided URL
      * 
      * @param urlstring The URL to which to POST
      * @param data The JSON body
      * @return The HTTP response to the request
      * @throws ConnectionException When a communication issue arises
      */
    def post ( urlstring: String, data: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).POST.<<( data, "application/json" ).as_str )
        http.shutdown
        new Response( resp )
    }
}

// vim: set ts=4 sw=4 et:
