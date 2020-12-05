/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
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
* org/omg/CORBA/OperationDescriptionHelper.java .
* IGNORE Generated by the IDL-to-Java compiler (portable), version "3.2"
* from idlj/src/main/java/com/sun/tools/corba/ee/idl/ir.idl
* IGNORE Sunday, January 21, 2018 1:54:23 PM EST
*/

abstract public class OperationDescriptionHelper
{
  private static String  _id = "IDL:omg.org/CORBA/OperationDescription:1.0";

  public static void insert (org.omg.CORBA.Any a, org.omg.CORBA.OperationDescription that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static org.omg.CORBA.OperationDescription extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [9];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.IdentifierHelper.id (), "Identifier", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.RepositoryIdHelper.id (), "RepositoryId", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "id",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.RepositoryIdHelper.id (), "RepositoryId", _tcOf_members0);
          _members0[2] = new org.omg.CORBA.StructMember (
            "defined_in",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.VersionSpecHelper.id (), "VersionSpec", _tcOf_members0);
          _members0[3] = new org.omg.CORBA.StructMember (
            "version",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_TypeCode);
          _members0[4] = new org.omg.CORBA.StructMember (
            "result",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.OperationModeHelper.type ();
          _members0[5] = new org.omg.CORBA.StructMember (
            "mode",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.IdentifierHelper.id (), "Identifier", _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.ContextIdentifierHelper.id (), "ContextIdentifier", _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.ContextIdSeqHelper.id (), "ContextIdSeq", _tcOf_members0);
          _members0[6] = new org.omg.CORBA.StructMember (
            "contexts",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ParameterDescriptionHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.ParDescriptionSeqHelper.id (), "ParDescriptionSeq", _tcOf_members0);
          _members0[7] = new org.omg.CORBA.StructMember (
            "parameters",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ExceptionDescriptionHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (org.omg.CORBA.ExcDescriptionSeqHelper.id (), "ExcDescriptionSeq", _tcOf_members0);
          _members0[8] = new org.omg.CORBA.StructMember (
            "exceptions",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (org.omg.CORBA.OperationDescriptionHelper.id (), "OperationDescription", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static org.omg.CORBA.OperationDescription read (org.omg.CORBA.portable.InputStream istream)
  {
    org.omg.CORBA.OperationDescription value = new org.omg.CORBA.OperationDescription ();
    value.name = istream.read_string ();
    value.id = istream.read_string ();
    value.defined_in = istream.read_string ();
    value.version = istream.read_string ();
    value.result = istream.read_TypeCode ();
    value.mode = org.omg.CORBA.OperationModeHelper.read (istream);
    value.contexts = org.omg.CORBA.ContextIdSeqHelper.read (istream);
    value.parameters = org.omg.CORBA.ParDescriptionSeqHelper.read (istream);
    value.exceptions = org.omg.CORBA.ExcDescriptionSeqHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, org.omg.CORBA.OperationDescription value)
  {
    ostream.write_string (value.name);
    ostream.write_string (value.id);
    ostream.write_string (value.defined_in);
    ostream.write_string (value.version);
    ostream.write_TypeCode (value.result);
    org.omg.CORBA.OperationModeHelper.write (ostream, value.mode);
    org.omg.CORBA.ContextIdSeqHelper.write (ostream, value.contexts);
    org.omg.CORBA.ParDescriptionSeqHelper.write (ostream, value.parameters);
    org.omg.CORBA.ExcDescriptionSeqHelper.write (ostream, value.exceptions);
  }

}
