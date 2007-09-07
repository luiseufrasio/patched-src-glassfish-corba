/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2002-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA_2_3.portable.InputStream;

import com.sun.corba.se.spi.ior.IOR;
import com.sun.corba.se.spi.ior.IORFactories ;

import com.sun.corba.se.spi.orb.ORB;

import com.sun.corba.se.spi.servicecontext.ServiceContexts;
import com.sun.corba.se.spi.servicecontext.ServiceContextDefaults;
import com.sun.corba.se.spi.ior.iiop.GIOPVersion;

import com.sun.corba.se.impl.encoding.CDRInputStream;
import com.sun.corba.se.impl.encoding.CDROutputStream;
import com.sun.corba.se.impl.encoding.CDRInputStream_1_2;
import com.sun.corba.se.impl.encoding.CDROutputStream_1_2;

import com.sun.corba.se.impl.orbutil.ORBUtility;
import com.sun.corba.se.impl.orbutil.ORBConstants;

import com.sun.corba.se.impl.logging.ORBUtilSystemException ;

import com.sun.corba.se.impl.orbutil.newtimer.TimingPoints ;

/**
 * This implements the GIOP 1.2 Reply header.
 *
 * @author Ram Jeyaraman 05/14/2000
 * @version 1.0
 */

public final class ReplyMessage_1_2 extends Message_1_2
        implements ReplyMessage {

    // Instance variables

    private ORB orb = null;
    private TimingPoints tp = null ;
    private ORBUtilSystemException wrapper = null ;
    private int reply_status = (int) 0;
    private ServiceContexts service_contexts = null ;
    private IOR ior = null;
    private String exClassName = null;
    private int minorCode = (int) 0;
    private CompletionStatus completionStatus = null;
    private short addrDisposition = KeyAddr.value; // default;
    
    // Constructors

    ReplyMessage_1_2(ORB orb) {
	this.service_contexts = ServiceContextDefaults.makeServiceContexts( orb ) ;
        this.orb = orb;
	this.tp = orb.getTimerManager().points() ;
	this.wrapper = orb.getLogWrapperTable().get_RPC_PROTOCOL_ORBUtil() ;
    }

    ReplyMessage_1_2(ORB orb, int _request_id, int _reply_status,
            ServiceContexts _service_contexts, IOR _ior) {
        super(Message.GIOPBigMagic, GIOPVersion.V1_2, FLAG_NO_FRAG_BIG_ENDIAN,
            Message.GIOPReply, 0);
        this.orb = orb;
	this.tp = orb.getTimerManager().points() ;
	this.wrapper = orb.getLogWrapperTable().get_RPC_PROTOCOL_ORBUtil() ;
        request_id = _request_id;
        reply_status = _reply_status;
        service_contexts = _service_contexts;
	if (service_contexts == null)
	    service_contexts = ServiceContextDefaults.makeServiceContexts( orb ) ;
        ior = _ior;
    }

    // Accessor methods

    public int getRequestId() {
        return this.request_id;
    }

    public int getReplyStatus() {
        return this.reply_status;
    }

    public short getAddrDisposition() {
        return this.addrDisposition;
    }
    
    public ServiceContexts getServiceContexts() {
        return this.service_contexts;
    }

    public SystemException getSystemException(String message) {
	return MessageBase.getSystemException(
            exClassName, minorCode, completionStatus, message, wrapper);
    }

    public IOR getIOR() {
        return this.ior;
    }

    public void setIOR( IOR ior ) {
	this.ior = ior;
    }

    // IO methods
    public void read(org.omg.CORBA.portable.InputStream istream) {
	tp.enter_giopHeaderReadReply() ;
	try {
	    super.read(istream);
	    this.request_id = istream.read_ulong();
	    this.reply_status = istream.read_long();
	    isValidReplyStatus(this.reply_status); // raises exception on error
	    this.service_contexts = ServiceContextDefaults.makeServiceContexts(
		(org.omg.CORBA_2_3.portable.InputStream)istream ) ;

	    // CORBA formal 00-11-0 15.4.2.2 GIOP 1.2 body must be
	    // aligned on an 8 octet boundary.
	    // Ensures that the first read operation called from the stub code,
	    // during body deconstruction, would skip the header padding, that was
	    // inserted to ensure that the body was aligned on an 8-octet boundary.
	    ((CDRInputStream)istream).setHeaderPadding(true);

	    // The code below reads the reply body in some cases
	    // SYSTEM_EXCEPTION & LOCATION_FORWARD & LOCATION_FORWARD_PERM &
	    // NEEDS_ADDRESSING_MODE
	    if (this.reply_status == SYSTEM_EXCEPTION) {

		String reposId = istream.read_string();
		this.exClassName = ORBUtility.classNameOf(reposId);
		this.minorCode = istream.read_long();
		int status = istream.read_long();

		switch (status) {
		case CompletionStatus._COMPLETED_YES:
		    this.completionStatus = CompletionStatus.COMPLETED_YES;
		    break;
		case CompletionStatus._COMPLETED_NO:
		    this.completionStatus = CompletionStatus.COMPLETED_NO;
		    break;
		case CompletionStatus._COMPLETED_MAYBE:
		    this.completionStatus = CompletionStatus.COMPLETED_MAYBE;
		    break;
		default:
		    throw wrapper.badCompletionStatusInReply( 
			CompletionStatus.COMPLETED_MAYBE, status );
		}

	    } else if (this.reply_status == USER_EXCEPTION) {
		// do nothing. The client stub will read the exception from body.
	    } else if ( (this.reply_status == LOCATION_FORWARD) ||
		    (this.reply_status == LOCATION_FORWARD_PERM) ){
		CDRInputStream cdr = (CDRInputStream) istream;
		this.ior = IORFactories.makeIOR( orb, (InputStream)cdr ) ;
	    }  else if (this.reply_status == NEEDS_ADDRESSING_MODE) {
		// read GIOP::AddressingDisposition from body and resend the
		// original request using the requested addressing mode. The
		// resending is transparent to the client program.
		this.addrDisposition = AddressingDispositionHelper.read(istream);            
	    }
	} finally {
	    tp.exit_giopHeaderReadReply() ;
	}
    }

    // Note, this writes only the header information. SystemException or
    // IOR or GIOP::AddressingDisposition may be written afterwards into the
    // reply mesg body.
    public void write(org.omg.CORBA.portable.OutputStream ostream) {
	tp.enter_giopHeaderReadReply() ;
	try {
	    super.write(ostream);
	    ostream.write_ulong(this.request_id);
	    ostream.write_long(this.reply_status);
	    service_contexts.write(
		(org.omg.CORBA_2_3.portable.OutputStream) ostream,
		GIOPVersion.V1_2);

	    // CORBA formal 00-11-0 15.4.2.2 GIOP 1.2 body must be
	    // aligned on an 8 octet boundary.
	    // Ensures that the first write operation called from the stub code,
	    // during body construction, would insert a header padding, such that
	    // the body is aligned on an 8-octet boundary.
	    ((CDROutputStream)ostream).setHeaderPadding(true);
	} finally {
	    tp.exit_giopHeaderReadReply() ;
	}
    }

    // Static methods
    public static void isValidReplyStatus(int replyStatus) {
        switch (replyStatus) {
        case NO_EXCEPTION :
        case USER_EXCEPTION :
        case SYSTEM_EXCEPTION :
        case LOCATION_FORWARD :
        case LOCATION_FORWARD_PERM :
        case NEEDS_ADDRESSING_MODE :
            break;
        default :
	    ORBUtilSystemException localWrapper = 
		ORB.getStaticLogWrapperTable().get_RPC_PROTOCOL_ORBUtil() ;
	    throw localWrapper.illegalReplyStatus( CompletionStatus.COMPLETED_MAYBE);
        }
    }

    public void callback(MessageHandler handler)
        throws java.io.IOException
    {
        handler.handleInput(this);
    }
} // class ReplyMessage_1_2
