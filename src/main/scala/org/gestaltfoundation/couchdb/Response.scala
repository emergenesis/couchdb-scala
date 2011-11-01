package org.gestaltfoundation.couchdb

import net.liftweb.json.{DefaultFormats,parse}

/** Represents a response from CouchDB
  *
  * Contains convenience methods for working with responses
  *
  * @param raw_response The response string received from the server
  */
class Response ( val raw_response: String ) {
    implicit val formats = DefaultFormats
    val json = parse ( raw_response )
    
    /** Transform the JSON response info an object from a case class
      *
      * @tparam T The case class into which object the JSON will be transformed
      * @return The resulting object
      */
    def as[T] ( implicit m: Manifest[T] ): T = {
        json.extract[T]
    }

    /** Does the response contain an error message?
      *
      * This only returns true after the creation of a successful response,
      * meaning that the CouchDB was able to process the request, but that the
      * request could not be performed; e.g. the object does not exist.
      *
      * @return Whether the response represents an error
      */
    def isError: Boolean = {
        val err = json.extractOrElse ( Error("NONE", "") )
        if ( err.error == "NONE" ) false else true
    }
}

// vim: set ts=4 sw=4 et:
