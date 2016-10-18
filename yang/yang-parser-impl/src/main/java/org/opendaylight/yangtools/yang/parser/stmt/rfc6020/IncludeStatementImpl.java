/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yangtools.yang.parser.stmt.rfc6020;

import static org.opendaylight.yangtools.yang.parser.spi.meta.ModelProcessingPhase.SOURCE_LINKAGE;
import static org.opendaylight.yangtools.yang.parser.spi.meta.StmtContextUtils.firstAttributeOf;

import com.google.common.base.Optional;
import java.util.Collection;
import java.util.Date;
import org.opendaylight.yangtools.yang.common.SimpleDateFormatUtil;
import org.opendaylight.yangtools.yang.model.api.ModuleIdentifier;
import org.opendaylight.yangtools.yang.model.api.Rfc6020Mapping;
import org.opendaylight.yangtools.yang.model.api.meta.EffectiveStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.IncludeStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.PrefixStatement;
import org.opendaylight.yangtools.yang.model.api.stmt.RevisionDateStatement;
import org.opendaylight.yangtools.yang.parser.builder.impl.ModuleIdentifierImpl;
import org.opendaylight.yangtools.yang.parser.spi.SubmoduleNamespace;
import org.opendaylight.yangtools.yang.parser.spi.SubstatementValidator;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.opendaylight.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.opendaylight.yangtools.yang.parser.spi.meta.InferenceException;
import org.opendaylight.yangtools.yang.parser.spi.meta.ModelActionBuilder;
import org.opendaylight.yangtools.yang.parser.spi.meta.ModelActionBuilder.InferenceAction;
import org.opendaylight.yangtools.yang.parser.spi.meta.ModelActionBuilder.Prerequisite;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext;
import org.opendaylight.yangtools.yang.parser.spi.meta.StmtContext.Mutable;
import org.opendaylight.yangtools.yang.parser.spi.source.IncludedSubmoduleNameToIdentifier;
import org.opendaylight.yangtools.yang.parser.stmt.rfc6020.effective.IncludeEffectiveStatementImpl;

public class IncludeStatementImpl extends AbstractDeclaredStatement<String> implements IncludeStatement {
    private static final SubstatementValidator SUBSTATEMENT_VALIDATOR = SubstatementValidator.builder(
        Rfc6020Mapping.INCLUDE).addOptional(Rfc6020Mapping.REVISION_DATE).build();

    protected IncludeStatementImpl(final StmtContext<String, IncludeStatement, ?> context) {
        super(context);
    }

    public static class Definition extends
            AbstractStatementSupport<String, IncludeStatement, EffectiveStatement<String, IncludeStatement>> {

        public Definition() {
            super(Rfc6020Mapping.INCLUDE);
        }

        @Override
        public String parseArgumentValue(final StmtContext<?, ?, ?> ctx, final String value) {
            return value;
        }

        @Override
        public IncludeStatement createDeclared(final StmtContext<String, IncludeStatement, ?> ctx) {
            return new IncludeStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, IncludeStatement> createEffective(
                final StmtContext<String, IncludeStatement, EffectiveStatement<String, IncludeStatement>> ctx) {
            return new IncludeEffectiveStatementImpl(ctx);
        }

        @Override
        public void onLinkageDeclared(
                final Mutable<String, IncludeStatement, EffectiveStatement<String, IncludeStatement>> stmt) {
            final ModuleIdentifier includeSubmoduleIdentifier = getIncludeSubmoduleIdentifier(stmt);

            ModelActionBuilder includeAction = stmt.newInferenceAction(SOURCE_LINKAGE);
            final Prerequisite<StmtContext<?, ?, ?>> requiresCtxPrerequisite = includeAction.requiresCtx(stmt,
                    SubmoduleNamespace.class, includeSubmoduleIdentifier, SOURCE_LINKAGE);

            includeAction.apply(new InferenceAction() {
                @Override
                public void apply() {
                    StmtContext<?, ?, ?> includedSubModuleContext = requiresCtxPrerequisite.get();

                    stmt.addToNs(IncludedModuleContext.class, includeSubmoduleIdentifier,
                            includedSubModuleContext);
                    stmt.addToNs(IncludedSubmoduleNameToIdentifier.class,
                            stmt.getStatementArgument(), includeSubmoduleIdentifier);
                }

                @Override
                public void prerequisiteFailed(final Collection<? extends Prerequisite<?>> failed) {
                    InferenceException.throwIf(failed.contains(requiresCtxPrerequisite),
                        stmt.getStatementSourceReference(),
                        "Included submodule '%s' was not found: ", stmt.getStatementArgument());
                }
            });
        }

        private static ModuleIdentifier getIncludeSubmoduleIdentifier(final Mutable<String, IncludeStatement, ?> stmt) {

            String subModuleName = stmt.getStatementArgument();

            Date revisionDate = firstAttributeOf(stmt.declaredSubstatements(), RevisionDateStatement.class);
            if (revisionDate == null) {
                revisionDate = SimpleDateFormatUtil.DEFAULT_DATE_IMP;
            }

            return new ModuleIdentifierImpl(subModuleName, Optional.absent(), Optional.of(revisionDate));
        }

        @Override
        public void onFullDefinitionDeclared(final Mutable<String, IncludeStatement,
                EffectiveStatement<String, IncludeStatement>> stmt) {
            super.onFullDefinitionDeclared(stmt);
            SUBSTATEMENT_VALIDATOR.validate(stmt);
        }
    }

    @Override
    public String getModule() {
        return argument();
    }

    @Override
    public PrefixStatement getPrefix() {
        return firstDeclared(PrefixStatement.class);
    }

    @Override
    public RevisionDateStatement getRevisionDate() {
        return firstDeclared(RevisionDateStatement.class);
    }

}
