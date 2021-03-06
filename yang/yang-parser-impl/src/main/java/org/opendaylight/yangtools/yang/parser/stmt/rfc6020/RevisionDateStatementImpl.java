/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import java.text.ParseException;
import java.util.Date;
import org.opendaylight.yangtools.yang.common.SimpleDateFormatUtil;
import org.opendaylight.yangtools.yang.model.api.YangStmtMapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.RevisionDateStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.spi.meta.SubstatementValidator;
import org.opendaylight.yangtools.yang.parser.spi.source.SourceException;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.RevisionDateEffectiveStatementImpl;

public class RevisionDateStatementImpl extends AbstractDeclaredStatement<Date> implements RevisionDateStatement {
    private static final SubstatementValidator SUBSTATEMENT_VALIDATOR =
            SubstatementValidator.builder(YangStmtMapping.REVISION_DATE).build();

    protected RevisionDateStatementImpl(final StmtContext<Date, RevisionDateStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<Date, RevisionDateStatement, EffectiveStatement<Date, RevisionDateStatement>> {

        public Definition() {
            super(YangStmtMapping.REVISION_DATE);
        }

        @Override
        public Date parseArgumentValue(final StmtContext<?, ?, ?> ctx, final String value) {
            try {
                return SimpleDateFormatUtil.getRevisionFormat().parse(value);
            } catch (ParseException e) {
                throw new SourceException(ctx.getStatementSourceReference(), e,
                    "Revision value %s is not in required format yyyy-MM-dd", value);
            }
        }

        @Override
        public RevisionDateStatement createDeclared(final StmtContext<Date, RevisionDateStatement, ?> ctx) {
            return new RevisionDateStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<Date, RevisionDateStatement> createEffective(
                final StmtContext<Date, RevisionDateStatement, EffectiveStatement<Date, RevisionDateStatement>> ctx) {
            return new RevisionDateEffectiveStatementImpl(ctx);
        }

        @Override
        protected SubstatementValidator getSubstatementValidator() {
            return SUBSTATEMENT_VALIDATOR;
        }
    }

    @Override
    public Date getDate() {
        return argument();
    }
}
