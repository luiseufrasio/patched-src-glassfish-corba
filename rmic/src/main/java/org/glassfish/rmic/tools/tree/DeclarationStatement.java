/*
 * Copyright (c) 1994, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.glassfish.rmic.tools.tree;

import org.glassfish.rmic.tools.java.*;
import java.io.PrintStream;
import org.glassfish.rmic.tools.asm.Assembler;
import java.util.Hashtable;

/**
 * WARNING: The contents of this source file are not part of any
 * supported API.  Code that depends on them does so at its own risk:
 * they are subject to change or removal without notice.
 */
public
class DeclarationStatement extends Statement {
    int mod;
    Expression type;
    Statement args[];

    /**
     * Constructor
     */
    public DeclarationStatement(long where, int mod, Expression type, Statement args[]) {
        super(DECLARATION, where);
        this.mod = mod;
        this.type = type;
        this.args = args;
    }

    /**
     * Check statement
     * Report an error unless the call is checkBlockStatement.
     */
    Vset check(Environment env, Context ctx, Vset vset, Hashtable<Object, Object> exp) {
        env.error(where, "invalid.decl");
        return checkBlockStatement(env, ctx, vset, exp);
    }
    Vset checkBlockStatement(Environment env, Context ctx, Vset vset, Hashtable<Object, Object> exp) {
        if (labels != null) {
            env.error(where, "declaration.with.label", labels[0]);
        }
        vset = reach(env, vset);
        Type t = type.toType(env, ctx);

        for (int i = 0 ; i < args.length ; i++) {
            vset = args[i].checkDeclaration(env, ctx, vset, mod, t, exp);
        }

        return vset;
    }

    /**
     * Inline
     */
    public Statement inline(Environment env, Context ctx) {
        int n = 0;
        for (int i = 0 ; i < args.length ; i++) {
            if ((args[i] = args[i].inline(env, ctx)) != null) {
                n++;
            }
        }
        return (n == 0) ? null : this;
    }

    /**
     * Create a copy of the statement for method inlining
     */
    public Statement copyInline(Context ctx, boolean valNeeded) {
        DeclarationStatement s = (DeclarationStatement)clone();
        if (type != null) {
            s.type = type.copyInline(ctx);
        }
        s.args = new Statement[args.length];
        for (int i = 0; i < args.length; i++){
            if (args[i] != null){
                s.args[i] = args[i].copyInline(ctx, valNeeded);
            }
        }
        return s;
    }

    /**
     * The cost of inlining this statement
     */
    public int costInline(int thresh, Environment env, Context ctx) {
        int cost = 1;
        for (int i = 0; i < args.length; i++){
            if (args[i] != null){
                cost += args[i].costInline(thresh, env, ctx);
            }
        }
        return cost;
    }


    /**
     * Code
     */
    public void code(Environment env, Context ctx, Assembler asm) {
        for (int i = 0 ; i < args.length ; i++) {
            if (args[i] != null) {
                args[i].code(env, ctx, asm);
            }
        }
    }

    /**
     * Print
     */
    public void print(PrintStream out, int indent) {
        out.print("declare ");
        super.print(out, indent);
        type.print(out);
        out.print(" ");
        for (int i = 0 ; i < args.length ; i++) {
            if (i > 0) {
                out.print(", ");
            }
            if (args[i] != null)  {
                args[i].print(out);
            } else {
                out.print("<empty>");
            }
        }
        out.print(";");
    }
}
