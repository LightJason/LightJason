package {{{ package }}}.agents;

{{ #environmentactionexist }}
import org.lightjason.agentspeak.action.binding.IAgentAction;
import org.lightjason.agentspeak.action.binding.IAgentActionFilter;
import org.lightjason.agentspeak.action.binding.IAgentActionName;
{{ /environmentactionexist }}
import org.lightjason.agentspeak.action.IAction;
import org.lightjason.agentspeak.agent.IAgent;
import org.lightjason.agentspeak.agent.IBaseAgent;
import org.lightjason.agentspeak.agent.IPlanBundle;
import org.lightjason.agentspeak.beliefbase.IBeliefbaseOnDemand;
import org.lightjason.agentspeak.common.CCommon;
import org.lightjason.agentspeak.configuration.IAgentConfiguration;
import org.lightjason.agentspeak.generator.IBaseAgentGenerator;
import org.lightjason.agentspeak.language.CLiteral;
import org.lightjason.agentspeak.language.CRawTerm;
import org.lightjason.agentspeak.language.ILiteral;
import org.lightjason.agentspeak.language.execution.IVariableBuilder;

import {{{ package }}}.environment.IEnvironment;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * abstract class to define an
 * agent with environment
 */
{{ #environmentactionexist }}@IAgentAction{{ /environmentactionexist }}
public abstract class IEnvironmentAgent<T extends IEnvironmentAgent<?>> extends IBaseAgent<IEnvironmentAgent<T>>
{
    /**
     * serial id
     */
    private static final long serialVersionUID = 639087239899834442L;
    /**
     * reference to environment
     */
    protected final IEnvironment m_environment;
    /**
     * agent name
     */
    private final String m_name;

    /**
     * ctor
     *
     * @param p_configuration agent configuration
     * @param p_environment environment reference
     * @param p_name agent name
     */
    protected IEnvironmentAgent( @Nonnull final IAgentConfiguration<IEnvironmentAgent<T>> p_configuration,
                                 @Nonnull final IEnvironment p_environment, @Nonnull final String p_name )
    {
        super( p_configuration );
        m_name = p_name;
        m_environment = p_environment;

        // add environment beliefbase to the agent with the prefix "env"
        m_beliefbase.add( new CEnvironmentBeliefbase().create( "env", m_beliefbase ) );
    }

    /**
     * returns a literal stream to get a view
     * of the agent depends on the calling agent
     *
     * @param p_agent calling agent
     * @return literal stream
     */
    @Nonnull
    public Stream<ILiteral> literal( @Nonnull final IEnvironmentAgent<?> p_agent )
    {
        return Stream.of( CLiteral.from( "agent", CLiteral.from( "name", CRawTerm.from( m_name ) ) ) );
    }

    /**
     * returns the agent name
     *
     * @return agent name
     */
    @Nonnull
    public final String name()
    {
        return m_name;
    }

    @Override
    public final int hashCode()
    {
        return m_name.hashCode();
    }

    @Override
    public final boolean equals( final Object p_object )
    {
        return ( p_object != null ) && ( p_object instanceof IEnvironmentAgent<?> ) && ( p_object.hashCode() == this.hashCode() );
    }



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * on-demand beliefbase to get access
     * to the environment data
     */
    private final class CEnvironmentBeliefbase extends IBeliefbaseOnDemand<IEnvironmentAgent<T>>
    {
        @Nonnull
        @Override
        public final Stream<ILiteral> streamLiteral()
        {
            return m_environment.literal( IEnvironmentAgent.this );
        }

        @Nonnull
        @Override
        public final Collection<ILiteral> literal( @Nonnull final String p_key )
        {
            return m_environment.literal( IEnvironmentAgent.this )
                                .filter( i -> p_key.equals( i.functor() ) )
                                .collect( Collectors.toSet() );
        }

        @Override
        public final boolean empty()
        {
            return !m_environment.literal( IEnvironmentAgent.this ).findFirst().isPresent();
        }

        @Override
        public final int size()
        {
            return (int) m_environment.literal( IEnvironmentAgent.this ).count();
        }

        @Override
        public final boolean containsLiteral( @Nonnull final String p_key )
        {
            return m_environment.literal( IEnvironmentAgent.this )
                                .anyMatch( i -> p_key.equals( i.functor() ) );
        }

    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * abstract agent generatorclass for all agents
     */
    protected abstract static class IGenerator<T extends IEnvironmentAgent<?>> extends IBaseAgentGenerator<IEnvironmentAgent<T>>
    {
        /**
         * agent number counter
         */
        protected final AtomicInteger m_counter = new AtomicInteger();
        /**
         * environment reference
         */
        protected final IEnvironment m_environment;
        /**
         * map with agent names and agent objects
         */
        private final Map<String, IAgent<?>> m_agents;



        /**
         * constructor
         *
         * @param p_stream ASL input stream
         * @param p_actions action stream
         * @param p_environment environment reference
         * @param p_agents agent map
         * @throws Exception on any error
         */
        protected IGenerator( @Nonnull final InputStream p_stream, @Nonnull final Stream<IAction> p_actions,
                              @Nonnull final IEnvironment p_environment, @Nonnull final Map<String, IAgent<?>> p_agents ) throws Exception
        {
            super( p_stream,
                   Stream.concat(
                       CCommon.actionsFromAgentClass( IEnvironmentAgent.class ),
                       p_actions
                   ).collect( Collectors.toSet() )
            );

            m_agents = p_agents;
            m_environment = p_environment;
        }


        /**
         * constructor
         *
         * @param p_stream ASL input stream
         * @param p_actions action stream
         * @param p_variablebuilder variable builder
         * @param p_environment environment reference
         * @param p_agents agent map
         * @throws Exception on any error
         */
        protected IGenerator( @Nonnull final InputStream p_stream, @Nonnull final Stream<IAction> p_actions,
                              @Nonnull final IVariableBuilder p_variablebuilder, @Nonnull final IEnvironment p_environment,
                              @Nonnull final Map<String, IAgent<?>> p_agents ) throws Exception
        {
            super( p_stream,
                   Stream.concat(
                       CCommon.actionsFromAgentClass( IEnvironmentAgent.class ),
                       p_actions
                   ).collect( Collectors.toSet() ),
                   p_variablebuilder
            );

            m_agents = p_agents;
            m_environment = p_environment;
        }


        /**
         * constructor
         *
         * @param p_stream ASL input stream
         * @param p_actions action stream
         * @param p_planbundle planbundles
         * @param p_variablebuilder variable builder
         * @param p_environment environment reference
         * @param p_agents agent map
         * @throws Exception on any error
         */
        protected IGenerator( @Nonnull final InputStream p_stream, @Nonnull final Stream<IAction> p_actions,
                              @Nonnull final Set<IPlanBundle> p_planbundle, @Nonnull final IVariableBuilder p_variablebuilder,
                              @Nonnull final IEnvironment p_environment, @Nonnull final Map<String, IAgent<?>> p_agents ) throws Exception
        {
            super( p_stream,
                   Stream.concat(
                       CCommon.actionsFromAgentClass( IEnvironmentAgent.class ),
                       p_actions
                   ).collect( Collectors.toSet() ),
                   p_planbundle,
                   p_variablebuilder
            );

            m_agents = p_agents;
            m_environment = p_environment;
        }


        /**
         * initialize the agent for the simulation
         *
         * @param p_agent agent object
         * @return agent object
         */
        @Nonnull
        protected final T initializeagent( @Nonnull final T p_agent )
        {
            m_agents.putIfAbsent( p_agent.name(), p_agent );
            return m_environment.initializeagent( p_agent );
        }

    }
}
