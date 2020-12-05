/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Eclipse Distribution License
 * v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License v2.0
 * w/Classpath exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause OR GPL-2.0 WITH
 * Classpath-exception-2.0
 */

package org.omg.CORBA;

/**
 * The Holder for <tt>ServiceInformation</tt>.  For more information on 
 * Holder files, see <a href="doc-files/generatedfiles.html#holder">
 * "Generated Files: Holder Files"</a>.<P>
 * A Holder class for a <code>ServiceInformation</code> object
 * that is used to store "out" and "inout" parameters in IDL methods.
 * If an IDL method signature has an IDL <code>xxx</code> as an "out"
 * or "inout" parameter, the programmer must pass an instance of
 * <code>ServiceInformationHolder</code> as the corresponding
 * parameter in the method invocation; for "inout" parameters, the programmer
 * must also fill the "in" value to be sent to the server.
 * Before the method invocation returns, the ORB will fill in the
 * value corresponding to the "out" value returned from the server.
 * <P>
 * If <code>myServiceInformationHolder</code> is an instance of <code>ServiceInformationHolder</code>,
 * the value stored in its <code>value</code> field can be accessed with
 * <code>myServiceInformationHolder.value</code>.
 */
public final class ServiceInformationHolder
    implements org.omg.CORBA.portable.Streamable {

    /**
     * The <code>ServiceInformation</code> value held by this
     * <code>ServiceInformationHolder</code> object in its <code>value</code> field.
     */
    public ServiceInformation value;

    /**
     * Constructs a new <code>ServiceInformationHolder</code> object with its
     * <code>value</code> field initialized to null.
     */
    public ServiceInformationHolder() {
        this(null);
    }
        
    /**
     * Constructs a new <code>ServiceInformationHolder</code> object with its
     * <code>value</code> field initialized to the given
     * <code>ServiceInformation</code> object.
     *
     * @param arg the <code>ServiceInformation</code> object with which to initialize
     *                the <code>value</code> field of the newly-created
     *                <code>ServiceInformationHolder</code> object
     */
    public ServiceInformationHolder(org.omg.CORBA.ServiceInformation arg) {
        value = arg;
    }


    /**
     * Marshals the value in this <code>ServiceInformationHolder</code> object's
     * <code>value</code> field to the output stream <code>out</code>.
     *
     * @param out the <code>OutputStream</code> object that will contain
     *               the CDR formatted data
     */
    public void _write(org.omg.CORBA.portable.OutputStream out) {
        org.omg.CORBA.ServiceInformationHelper.write(out, value);
    }

    /**
     * Reads unmarshalled data from the input stream <code>in</code> and assigns it to
     * the <code>value</code> field in this <code>ServiceInformationHolder</code> object.
     *
     * @param in the <code>InputStream</code> object containing CDR 
     *              formatted data from the wire
     */
    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = org.omg.CORBA.ServiceInformationHelper.read(in);
    }

    /**
     * Retrieves the <code>TypeCode</code> object that corresponds
     * to the value held in this <code>ServiceInformationHolder</code> object's
     * <code>value</code> field.
     *
     * @return    the type code for the value held in this <code>ServiceInformationHolder</code>
     *            object
     */
    public org.omg.CORBA.TypeCode _type() {
        return org.omg.CORBA.ServiceInformationHelper.type();
    }
}

