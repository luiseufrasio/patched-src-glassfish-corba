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

 /**
 * The Helper for <tt>ServiceInformation</tt>.  For more information on 
* Helper files, see <a href="doc-files/generatedfiles.html#helper">
* "Generated Files: Helper Files"</a>.<P>
*/

package org.omg.CORBA;


public abstract class ServiceInformationHelper {

    public static void write(org.omg.CORBA.portable.OutputStream out, org.omg.CORBA.ServiceInformation that)
    {
        out.write_long(that.service_options.length);
        out.write_ulong_array(that.service_options, 0, that.service_options.length);
        out.write_long(that.service_details.length);
        for (int i = 0 ; i < that.service_details.length ; i += 1) {
            org.omg.CORBA.ServiceDetailHelper.write(out, that.service_details[i]);
        }
    }

    public static org.omg.CORBA.ServiceInformation read(org.omg.CORBA.portable.InputStream in) {
        org.omg.CORBA.ServiceInformation that = new org.omg.CORBA.ServiceInformation();
        {
            int __length = in.read_long();
            that.service_options = new int[__length];
            in.read_ulong_array(that.service_options, 0, that.service_options.length);
        }
        {
            int __length = in.read_long();
            that.service_details = new org.omg.CORBA.ServiceDetail[__length];
            for (int __index = 0 ; __index < that.service_details.length ; __index += 1) {
                that.service_details[__index] = org.omg.CORBA.ServiceDetailHelper.read(in);
            }
        }
        return that;
    }
    public static org.omg.CORBA.ServiceInformation extract(org.omg.CORBA.Any a) {
        org.omg.CORBA.portable.InputStream in = a.create_input_stream();
        return read(in);
    }
    public static void insert(org.omg.CORBA.Any a, org.omg.CORBA.ServiceInformation that) {
        org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
        write(out, that);
        a.read_value(out.create_input_stream(), type());
    }
    private static org.omg.CORBA.TypeCode _tc;
    synchronized public static org.omg.CORBA.TypeCode type() {
        int _memberCount = 2;
        org.omg.CORBA.StructMember[] _members = null;
        if (_tc == null) {
            _members= new org.omg.CORBA.StructMember[2];
            _members[0] = new org.omg.CORBA.StructMember(
                                                         "service_options",
                                                         org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong)),
                                                         null);

            _members[1] = new org.omg.CORBA.StructMember(
                                                         "service_details",
                                                         org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ServiceDetailHelper.type()),
                                                         null);
            _tc = org.omg.CORBA.ORB.init().create_struct_tc(id(), "ServiceInformation", _members);
        }
        return _tc;
    }
    public static String id() {
        return "IDL:omg.org/CORBA/ServiceInformation:1.0";
    }
}
